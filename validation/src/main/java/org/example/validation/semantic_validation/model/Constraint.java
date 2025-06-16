package org.example.validation.semantic_validation.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Constraint {
    private String leftOperand;
    private String operator;
    private String rightOperand;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  {\n");
        sb.append("  \"leftOperand\": \"").append(leftOperand).append("\",\n");
        sb.append("  \"operator\": \"").append(operator).append("\",\n");
        sb.append("  \"rightOperand\": \"").append(rightOperand).append("\"\n");
        sb.append("  }\n");
        return sb.toString();
    }
}
