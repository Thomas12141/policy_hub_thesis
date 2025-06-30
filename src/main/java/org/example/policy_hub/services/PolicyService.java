package org.example.policy_hub.services;

import jakarta.transaction.Transactional;
import org.example.policy_hub.entities.PolicyEntity;
import org.example.policy_hub.repositories.PolicyRepository;
import org.example.validation.ParallelPolicyValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository repository;

    public PolicyService(PolicyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<String> save(String uid, String jsonContent) {
        ParallelPolicyValidator validator = new ParallelPolicyValidator();
        List<String> errors = validator.validate(jsonContent);
        if (!errors.isEmpty()) {
            return errors;
        }
        PolicyEntity entity = new PolicyEntity();
        entity.setUid(uid);
        entity.setJsonContent(jsonContent);
        entity.setUploadedAt(LocalDateTime.now());
        if(repository.findByUid(uid).isPresent()) {
            return List.of("Policy with uid " + uid + " already exists");
        }
        repository.save(entity);
        return Collections.emptyList();
    }

    public Optional<PolicyEntity> findByUid(String uid) {
        return repository.findByUid(uid);
    }

    public List<PolicyEntity> findAll() {
        return repository.findAll();
    }
}
