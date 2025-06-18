package org.example.policy_hub.services;

import org.example.validation.Type;
import org.example.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {
    private final Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public List<String> validate(String policy) {
        return validator.validate(policy, Type.JSON);
    }
}
