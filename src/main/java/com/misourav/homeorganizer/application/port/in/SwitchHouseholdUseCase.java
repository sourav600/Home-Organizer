package com.misourav.homeorganizer.application.port.in;

import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.UserId;

import java.time.Instant;

/**
 * Issues a new access token scoped to a different household for the authenticated user.
 * Fails if the user is not a member of the target household.
 */
public interface SwitchHouseholdUseCase {

    SwitchResult switchTo(UserId userId, HouseholdId targetHouseholdId);

    record SwitchResult(String accessToken,
                        Instant expiresAt,
                        String householdId,
                        String role) {}
}