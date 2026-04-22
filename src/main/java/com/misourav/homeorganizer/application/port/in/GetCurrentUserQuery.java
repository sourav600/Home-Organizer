package com.misourav.homeorganizer.application.port.in;

import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.UserId;

import java.util.Set;

public interface GetCurrentUserQuery {

    /**
     * Returns the user scoped to a specific household — role and permissions
     * depend on which household we're looking at.
     */
    CurrentUserView get(UserId userId, HouseholdId activeHouseholdId);

    record CurrentUserView(String userId,
                           String email,
                           String name,
                           String role,
                           String householdId,
                           Set<String> permissions) {}
}