package com.misourav.homeorganizer.domain.model;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        if (value == null) throw new IllegalArgumentException("UserId value cannot be null");
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId of(String value) {
        return new UserId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
