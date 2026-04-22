package com.misourav.homeorganizer.domain.model;

import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Role is a named grouping of directly-assigned permissions, positioned in a hierarchy.
 * A higher-level role inherits permissions from its descendants via the role hierarchy,
 * but that resolution is performed by the application layer (PermissionResolver),
 * not here — keeping this entity free of I/O and traversal logic.
 */
public final class Role {
    private final RoleId id;
    private final RoleCode code;
    private final String displayName;
    private final RoleId parentId;          // nullable — top role has no parent
    @Getter
    private final boolean system;
    private final Set<PermissionCode> directPermissions;

    public Role(RoleId id,
                RoleCode code,
                String displayName,
                RoleId parentId,
                boolean system,
                Set<PermissionCode> directPermissions) {
        this.id = Objects.requireNonNull(id);
        this.code = Objects.requireNonNull(code);
        this.displayName = Objects.requireNonNull(displayName);
        this.parentId = parentId;
        this.system = system;
        this.directPermissions = Collections.unmodifiableSet(
                new LinkedHashSet<>(Objects.requireNonNullElse(directPermissions, Set.of())));
    }

    public RoleId id() { return id; }
    public RoleCode code() { return code; }
    public String displayName() { return displayName; }
    public RoleId parentId() { return parentId; }
    public int level() { return code.level(); }

    public Set<PermissionCode> directPermissions() { return directPermissions; }

    public boolean outranks(Role other) {
        return this.code.outranks(other.code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
