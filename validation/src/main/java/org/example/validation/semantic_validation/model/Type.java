package org.example.validation.semantic_validation.model;

public enum Type {
    POLICY("Policy"), SET("Set"), OFFER("Offer"), AGREEMENT("Agreement");

    Type(String type) {
        this.type = type;
    }

    private final String type;

    @Override
    public String toString() {
        return type;
    }
}
