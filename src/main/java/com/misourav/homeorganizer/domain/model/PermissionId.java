package com.misourav.homeorganizer.domain.model;

import java.util.UUID;

public record PermissionId(UUID value) {
    public PermissionId {
        if (value == null) throw new IllegalArgumentException("PermissionId value cannot be null");
    }

    public static PermissionId newId() {
        return new PermissionId(UUID.randomUUID());
    }

    public static PermissionId of(UUID value) {
        return new PermissionId(value);
    }
}
