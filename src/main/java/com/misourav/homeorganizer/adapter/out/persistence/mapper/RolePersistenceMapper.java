package com.misourav.homeorganizer.adapter.out.persistence.mapper;

import com.misourav.homeorganizer.adapter.out.persistence.entity.RoleJpaEntity;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleId;

import java.util.LinkedHashSet;

public final class RolePersistenceMapper {

    private RolePersistenceMapper() {}

    public static Role toDomain(RoleJpaEntity e) {
        return new Role(
                RoleId.of(e.getId()),
                e.getCode(),
                e.getDisplayName(),
                e.getParentId() == null ? null : RoleId.of(e.getParentId()),
                e.isSystem(),
                new LinkedHashSet<>(e.getDirectPermissions())
        );
    }

    public static RoleJpaEntity toEntity(Role r) {
        return new RoleJpaEntity(
                r.id().value(),
                r.code(),
                r.displayName(),
                r.level(),
                r.parentId() == null ? null : r.parentId().value(),
                r.isSystem(),
                new LinkedHashSet<>(r.directPermissions())
        );
    }
}
