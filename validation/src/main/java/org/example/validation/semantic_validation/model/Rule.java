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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  {\n");
        sb.append("  \"action\": [");
        for (String action : getAction()) {
            sb.append("\"").append(action).append("\", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("],\n");
        if (getTarget() != null) {
            sb.append("  \"target\": \"").append(getTarget()).append("\",\n");
        }
        if (getConstraints() != null) {
            sb.append("  \"constraint\": [\n");
            for (Constraint constraint : getConstraints()) {
                sb.append(constraint.toString()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append("  ]\n");
        }
        sb.append("  }\n");

        return sb.toString();
    }
}
