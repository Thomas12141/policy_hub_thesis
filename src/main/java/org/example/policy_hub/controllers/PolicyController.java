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
    public ResponseEntity<String> uploadPolicy(@RequestBody String jsonPolicy) {
        try {
            String uid = extractUid(jsonPolicy);
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

    @GetMapping("/{uid}")
    public ResponseEntity<?> getPolicy(@PathVariable String uid) {
        return service.findByUid(uid)
                .map(policy -> ResponseEntity.ok().body(policy.getJsonContent()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<String> listAll() {
        return service.findAll().stream().map(PolicyEntity::getJsonContent).toList();
    }

    private String extractUid(String jsonPolicy) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(jsonPolicy);
        return json.get("uid").asText();
    }
}
