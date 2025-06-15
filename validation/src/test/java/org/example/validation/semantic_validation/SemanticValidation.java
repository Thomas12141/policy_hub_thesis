package org.example.validation.semantic_validation;

import org.example.validation.Type;
import org.example.validation.Validator;

import java.util.List;

public class SemanticValidation implements Validator {
    @Override
    public List<String> validate(String policy, Type type) {
        return List.of();
    }
}
