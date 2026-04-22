package com.misourav.homeorganizer.domain.model;

public record HashedPassword(String value) {
    public HashedPassword {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("HashedPassword cannot be blank");
        }
    }

    public static HashedPassword of(String value) {
        return new HashedPassword(value);
    }

    @Override
    public String toString() {
        return "HashedPassword[***]";
    }
}
