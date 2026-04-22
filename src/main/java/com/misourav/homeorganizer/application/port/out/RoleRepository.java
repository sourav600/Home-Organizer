package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.RoleId;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(RoleId id);
    Optional<Role> findByCode(RoleCode code);
    List<Role> findAll();

    /**
     * Return the given role plus every descendant in the role hierarchy,
     * i.e. all roles whose permissions should be inherited by this role.
     */
    List<Role> findSelfAndDescendants(RoleId rootId);
}
