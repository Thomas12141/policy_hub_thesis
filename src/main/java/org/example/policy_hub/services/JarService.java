package org.example.policy_hub.services;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.example.JarPackager;
import org.example.policy_hub.entities.JarEntity;
import org.example.policy_hub.repositories.JarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JarService {
    Logger logger = LoggerFactory.getLogger(JarService.class);
    private final Map<String, String> moduleToDescription = Map.of("AccessPolicy",
            "Grants for one or many defined participants access, every participant must have an id defined, the policy " +
                    "lets the provider to define several participants as allowed to access the asset.",
            "IsDataspaceMember","Checks if the connector is a participant of the dataspace.",
            "BillingPolicy", "Checks if the needed amount for the asset have been paid.",
            "LocationPolicy", "Checks if the asset is located in the defined region.",
            "TimeFramePolicy", "Checks if the timepoint is in the right frame.",
            "CountPolicy", "Checks if the number of the transfers is under the allowed border.");

    private final JarRepository repository;

    public JarService(JarRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws IOException {
        JarPackager packager = new JarPackager();
        String projectRoot = System.getProperty("user.dir");
        String modulesPath = projectRoot + "/policy_evaluation/edc_extension_storage";
        List<String> modules = Stream.of(Objects.requireNonNull(new File(modulesPath).listFiles())).
                map(File::getName).
                filter(folder -> !folder.endsWith("jars")).
                toList();

        Files.createDirectories(Paths.get(modulesPath + "/jars"));

        modules.forEach(moduleName -> packager.packModulesIntoJars(List.of(moduleName)));
        for ( String moduleName : modules) {
            if (repository.findByName(moduleName).isPresent()) {
                continue;
            }
            File jarFile = Objects.requireNonNull(
                    new File(modulesPath + "/jars").
                    listFiles(file -> file.getName().
                            contains(moduleName))
            )[0];
            JarEntity jarEntity = new JarEntity();
            jarEntity.setName(moduleName);
            jarEntity.setFilePath(jarFile.toPath().toString());
            jarEntity.setDescription(
                    moduleToDescription.get(moduleName) == null ? "No description for this extension."
                            : moduleToDescription.get(moduleName));
            repository.save(jarEntity);
        }
    }

    public List<JarEntity> getAllJars() {
        return repository.findAll();
    }

    public FileSystemResource getJarByName(String moduleName) {
        Optional<JarEntity> jarEntity = repository.findByName(moduleName);
        return jarEntity.map(entity -> new FileSystemResource(entity.getFilePath())).orElse(null);
    }

}
