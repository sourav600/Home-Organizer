package com.misourav.homeorganizer.domain.model;

import java.util.Objects;

public final class Permission {
    private final PermissionId id;
    private final PermissionCode code;
    private final String description;

    public Permission(PermissionId id, PermissionCode code, String description) {
        this.id = Objects.requireNonNull(id);
        this.code = Objects.requireNonNull(code);
        this.description = description;
    }

    public PermissionId id() { return id; }
    public PermissionCode code() { return code; }
    public String description() { return description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
