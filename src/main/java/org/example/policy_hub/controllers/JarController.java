package org.example.policy_hub.controllers;

import org.example.policy_hub.entities.JarEntity;
import org.example.policy_hub.services.JarService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jars")
public class JarController {
    private final JarService jarService;

    public JarController(JarService jarService) {
        this.jarService = jarService;
    }

    @GetMapping
    public ResponseEntity<List<JarEntity>> listAllJars() {
        return ResponseEntity.ok(jarService.getAllJars());
    }

    @GetMapping("/download/{moduleName}")
    public ResponseEntity<Resource> downloadJar(@PathVariable String moduleName) {
        try {
            Resource jarResource = jarService.getJarByName(moduleName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + moduleName + ".jar\"")
                    .body(jarResource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

