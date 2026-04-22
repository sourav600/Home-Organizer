package com.misourav.homeorganizer.domain.exception;

import com.misourav.homeorganizer.domain.model.RoleCode;

public class RoleNotFoundException extends DomainException {
    public RoleNotFoundException(RoleCode code) {
        super("Role not found: " + code);
    }
}
