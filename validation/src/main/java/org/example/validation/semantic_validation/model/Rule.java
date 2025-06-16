package org.example.validation.semantic_validation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Rule {
    private List<String> action;
    private String target;
    private List<Constraint> constraints;
}
