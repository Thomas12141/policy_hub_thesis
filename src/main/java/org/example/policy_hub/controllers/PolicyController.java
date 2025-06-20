package org.example.policy_hub.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.policy_hub.entities.PolicyEntity;
import org.example.policy_hub.services.PolicyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> uploadPolicy(@RequestBody String jsonPolicy) {
        try {
            String uid = extractUid(jsonPolicy);
            if(uid == null || uid.isBlank()) {
                return ResponseEntity.badRequest().body("uid is missing or blank");
            }
            List<String> errors = service.save(uid, jsonPolicy);
            if(errors.isEmpty() ) {
                return ResponseEntity.ok("Policy stored with UID: " + uid );
            } else {
                return ResponseEntity.badRequest().body(errors.stream().reduce("", (a, b) -> a + "\n" + b));
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid policy: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getPolicy(@RequestParam("uid") String uid) {
        return service.findByUid(uid)
                .map(policy -> ResponseEntity.ok().body(policy.getJsonContent()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<JsonNode>> listAll() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<JsonNode> policies = service.findAll().stream()
                    .map(PolicyEntity::getJsonContent)
                    .map(content -> {
                        try {
                            return mapper.readTree(content);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(policies);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String extractUid(String jsonPolicy) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(jsonPolicy);
        return json.get("uid") == null ? null : json.get("uid").asText();
    }
}
