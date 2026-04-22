package com.misourav.homeorganizer.config.security;

import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.UserId;

/**
 * Principal stored inside Spring Security's Authentication. All controllers
 * and @PreAuthorize expressions can reach this via @AuthenticationPrincipal.
 */
public record AuthenticatedUser(UserId userId, RoleCode role, HouseholdId householdId) {
}
