package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.domain.exception.RoleNotFoundException;
import com.misourav.homeorganizer.domain.model.PermissionCode;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.RoleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Set;

/**
 * Resolves a role's effective permissions by walking the role hierarchy downward
 * and unioning the permissions directly assigned to the role and every descendant.
 *
 * Example: HOUSEHOLDER -> HOUSEMAKER -> MEMBER -> CHILD
 *   resolve(HOUSEHOLDER) = perms(HOUSEHOLDER) ∪ perms(HOUSEMAKER) ∪ perms(MEMBER) ∪ perms(CHILD)
 */
@Service
@RequiredArgsConstructor
public class PermissionResolver {

    private final RoleRepository roleRepository;

    public Set<PermissionCode> resolveByRoleId(RoleId roleId) {
        Set<PermissionCode> result = EnumSet.noneOf(PermissionCode.class);
        for (Role r : roleRepository.findSelfAndDescendants(roleId)) {
            result.addAll(r.directPermissions());
        }
        return result;
    }

    public Set<PermissionCode> resolveByRoleCode(RoleCode code) {
        Role role = roleRepository.findByCode(code)
                .orElseThrow(() -> new RoleNotFoundException(code));
        return resolveByRoleId(role.id());
    }
}
