package org.example.validation.semantic_validation.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PolicyValidation {
    private Policy policy;
    private List<String> errors;
}
