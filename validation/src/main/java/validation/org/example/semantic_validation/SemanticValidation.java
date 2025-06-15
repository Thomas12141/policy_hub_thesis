package validation.org.example.semantic_validation;

import validation.org.example.Type;
import validation.org.example.Validator;

import java.util.List;

public class SemanticValidation implements Validator {
    @Override
    public List<String> validate(String policy, Type type) {
        return List.of();
    }
}
