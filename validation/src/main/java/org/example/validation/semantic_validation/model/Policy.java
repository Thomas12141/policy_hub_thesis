package org.example.validation.semantic_validation.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Setter
@Getter
public class Policy {
    private String uid;
    private String context;
    private String assignee;
    private String assigner;
    private Type type = Type.SET;
    private List<Permission> permissions;
    private List<Prohibition> prohibitions;
    private List<Duty> duties;
    private Map<String, Object> otherProperties;

    public static Policy ofJSON(String JSON) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return of(JSON, mapper);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON format", e);
        }
    }

    public static Policy ofYaml(String yamlContent) {
        try {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            return of(yamlContent, yamlMapper);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid YAML format", e);
        }
    }

    private static Policy of(String payload, ObjectMapper mapper) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(payload);

        Builder builder = Builder.newBuilder();

        if (rootNode.has("uid")) {
            builder.setUid(rootNode.get("uid").asText());
        }

        if (rootNode.has("@context")) {
            builder.setContext(rootNode.get("@context").asText());
        }

        if (rootNode.has("@type")) {
            String typeStr = rootNode.get("@type").asText();
            builder.setType(Type.valueOf(typeStr.toUpperCase()));
        }

        List<Permission> permissions = new ArrayList<>();
        if (rootNode.has("permission")) {
            JsonNode permissionsNode = rootNode.get("permission");
            if (permissionsNode.isArray()) {
                for (JsonNode permNode : permissionsNode) {
                    Permission permission = new Permission();

                    permissions.add((Permission) setRuleProperties(permNode, permission));
                }
            }
        }
        builder.setPermissions(permissions);

        List<Duty> duties = new ArrayList<>();
        if (rootNode.has("duty")) {
            JsonNode dutiesNode = rootNode.get("duty");
            if (dutiesNode.isArray()) {
                for (JsonNode dutyNode : dutiesNode) {
                    Duty duty = new Duty();
                    duties.add((Duty) setRuleProperties(dutyNode, duty));
                }
            }
        }
        builder.setDuties(duties);


        List<Prohibition> prohibitions = new ArrayList<>();
        if (rootNode.has("prohibition")) {
            JsonNode prohibitionsNode =rootNode.get("prohibition");
            if (prohibitionsNode.isArray()) {
                for (JsonNode prohibitionNode : prohibitionsNode) {
                    Prohibition prohibition = new Prohibition();
                    prohibitions.add((Prohibition) setRuleProperties(prohibitionNode, prohibition));
                }
            }
        }
        builder.setProhibitions(prohibitions);

        if (rootNode.has("assignee")) {
            builder.setAssignee(rootNode.get("assignee").asText());
        }

        if (rootNode.has("assigner")) {
            builder.setAssigner(rootNode.get("assigner").asText());
        }

        Map<String, Object> otherProps = new HashMap<>();
        rootNode.properties().iterator().forEachRemaining(entry -> {
            if (!Arrays.asList("uid", "@context", "@type", "permission",
                            "prohibition", "duty", "assignee", "assigner")
                    .contains(entry.getKey())) {
                otherProps.put(entry.getKey(), entry.getValue().asText());
            }
        });
        builder.setOtherProperties(otherProps);

        return builder.build();
    }

    private static Rule setRuleProperties(JsonNode node, Rule rule) {
        if (node.has("action")) {
            JsonNode actionNode = node.get("action");
            if (actionNode.isArray()) {
                List<String> actions = new ArrayList<>();
                actionNode.forEach(n -> actions.add(n.asText()));
                rule.setAction(actions);
            } else {
                rule.setAction(Collections.singletonList(actionNode.asText()));
            }
        }

        if (node.has("target")) {
            rule.setTarget(node.get("target").asText());
        }

        List<Constraint> constraints = new ArrayList<>();
        if (node.has("constraint")) {
            JsonNode constraintsNode = node.get("constraint");
            if (constraintsNode.isArray()) {
                for (JsonNode constraintNode : constraintsNode) {
                    Constraint constraint = new Constraint();
                    constraint.setLeftOperand(constraintNode.get("leftOperand").asText());
                    constraint.setOperator(constraintNode.get("operator").asText());
                    constraint.setRightOperand(constraintNode.get("rightOperand").asText());
                    constraints.add(constraint);
                }
            }
        }
        rule.setConstraints(constraints);

        return rule;
    }

    private Policy() {
    }

    public static class Builder {
        private final Policy policy;

        private Builder() {
            policy = new Policy();
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Policy build() {
            return policy;
        }


        private Builder setUid(String uid) {
            policy.uid = uid;
            return this;
        }

        private Builder setContext(String context) {
            policy.context = context;
            return this;
        }

        private Builder setAssignee(String assignee) {
            policy.assignee = assignee;
            return this;
        }

        private Builder setAssigner(String assigner) {
            policy.assigner = assigner;
            return this;
        }

        private Builder setType(Type type) {
            policy.type = type;
            return this;
        }

        private Builder setPermissions(List<Permission> permissions) {
            policy.permissions = permissions;
            return this;
        }

        private Builder setProhibitions(List<Prohibition> prohibitions) {
            policy.prohibitions = prohibitions;
            return this;
        }

        private Builder setDuties(List<Duty> duties) {
            policy.duties = duties;
            return this;
        }

        private Builder setOtherProperties(Map<String, Object> otherProperties) {
            policy.otherProperties = otherProperties;
            return this;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n").append("@type: \"").append(type.toString()).append("\",\n");

        sb.append("  \"uid\": \"").append(uid).append("\",\n");

        sb.append("  \"@context\": \"").append(context).append("\",\n");

        if (assignee != null) {
            sb.append("  \"assignee\": \"").append(assignee).append("\",\n");
        }

        if (assigner != null) {
            sb.append("  \"assigner\": \"").append(assigner).append("\",\n");
        }

        if(permissions != null) {
            sb.append("  \"permission\": [\n");

            for (Permission permission : permissions) {
                sb.append(permission.toString()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append("  ],\n");
        }

        if (duties != null) {
            sb.append("  \"duty\": [\n");
            for (Duty duty : duties) {
                sb.append(duty.toString()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append("  ],\n");
        }

        if (prohibitions != null) {
            sb.append("  \"prohibition\": [\n");
            for (Prohibition prohibition : prohibitions) {
                sb.append(prohibition.toString()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append("  ],\n");
        }

        if (otherProperties != null) {
            for (Map.Entry<String, Object> entry : otherProperties.entrySet()) {
                sb.append("  \"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\",\n");
            }
            sb.deleteCharAt(sb.length() - 2);
        }
        sb.append("\n}");

        return sb.toString();
    }
}