package com.misourav.homeorganizer.domain.model;

import java.util.UUID;

public record RoleId(UUID value) {
    public RoleId {
        if (value == null) throw new IllegalArgumentException("RoleId value cannot be null");
    }

    public static RoleId newId() {
        return new RoleId(UUID.randomUUID());
    }

    public static RoleId of(UUID value) {
        return new RoleId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
