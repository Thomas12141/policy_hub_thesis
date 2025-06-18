package org.example.policy_hub.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.policy_hub.entities.PolicyEntity;
import org.example.policy_hub.services.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> uploadPolicy(@RequestBody String jsonPolicy) {
        try {
            String uid = extractUid(jsonPolicy);
            PolicyEntity saved = service.save(uid, jsonPolicy);
            return ResponseEntity.ok("Policy stored with UID: " + saved.getUid());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid policy: " + e.getMessage());
        }
    }

    @GetMapping("/{uid}")
    public ResponseEntity<?> getPolicy(@PathVariable String uid) {
        return service.findByUid(uid)
                .map(policy -> ResponseEntity.ok().body(policy.getJsonContent()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<String> listAllUids() {
        return service.findAll().stream().map(PolicyEntity::getUid).toList();
    }

    private String extractUid(String jsonPolicy) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(jsonPolicy);
        return json.get("uid").asText();
    }
}
