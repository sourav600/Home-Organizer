package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.UserId;

import java.time.Instant;

public interface TokenProvider {

    IssuedToken issue(UserId userId, RoleCode role, HouseholdId householdId);

    /**
     * Validate and parse a token. Throws if invalid/expired.
     */
    ParsedToken parse(String rawToken);

    record IssuedToken(String token, Instant expiresAt) {}

    record ParsedToken(UserId userId, RoleCode role, HouseholdId householdId) {}
}
