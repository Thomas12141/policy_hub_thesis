package org.example.policy_hub.services;

import org.example.policy_hub.entities.PolicyEntity;
import org.example.policy_hub.repositry.PolicyRepository;
import org.example.validation.ConcurrentAllValidatorsValidator;
import org.example.validation.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository repository;

    public PolicyService(PolicyRepository repository) {
        this.repository = repository;
    }

    public List<String> save(String uid, String jsonContent) {
        ConcurrentAllValidatorsValidator validator = new ConcurrentAllValidatorsValidator();
        List<String> result = validator.validate(jsonContent, Type.JSON);
        if (!result.isEmpty()) {
            return result;
        }
        PolicyEntity entity = new PolicyEntity();
        entity.setUid(uid);
        entity.setJsonContent(jsonContent);
        entity.setUploadedAt(LocalDateTime.now());
        repository.save(entity);
        return List.of();
    }

    public Optional<PolicyEntity> findByUid(String uid) {
        return repository.findByUid(uid);
    }

    public List<PolicyEntity> findAll() {
        return repository.findAll();
    }
}
