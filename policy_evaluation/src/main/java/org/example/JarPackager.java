package org.example;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JarPackager {

    public void packModulesIntoJars(List<String> moduleNames) {
        String projectRoot = System.getProperty("user.dir");
        File projectDir = new File(projectRoot);

        try {
            GradleConnector connector = GradleConnector.newConnector()
                    .forProjectDirectory(projectDir);

            try (ProjectConnection connection = connector.connect()) {
                for (String moduleName : moduleNames) {
                    String gradleModulePath = ":policy_evaluation:edc_extension_storage:" + moduleName;

                    connection.newBuild()
                            .forTasks(gradleModulePath + ":jar")
                            .setStandardOutput(System.out)
                            .setStandardError(System.err)
                            .run();

                    File jarFile = Objects.requireNonNull(new File(getJarPath(projectRoot, moduleName)).listFiles())[0];
                    Files.copy(jarFile.toPath(),
                            projectDir.toPath().resolve("policy_evaluation/edc_extension_storage/jars/" + jarFile.getName())
                    , StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getJarPath(String projectRootPath, String moduleName) {
        return Paths.get(projectRootPath,
                        "policy_evaluation",
                        "edc_extension_storage",
                        moduleName,
                        "build",
                        "libs")
                .toString();
    }

}
