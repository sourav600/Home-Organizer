package com.misourav.homeorganizer.adapter.out.persistence.entity;

import com.misourav.homeorganizer.domain.model.PermissionCode;
import com.misourav.homeorganizer.domain.model.RoleCode;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles",
        indexes = @Index(name = "idx_role_code", columnList = "code", unique = true))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoleJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true, length = 32)
    private RoleCode code;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "is_system", nullable = false)
    private boolean system;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id",
                    foreignKey = @ForeignKey(name = "fk_role_perm_role")))
    @Column(name = "permission_code", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PermissionCode> directPermissions = new LinkedHashSet<>();
}
