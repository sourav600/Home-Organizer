package com.misourav.homeorganizer.application.port.in;

import java.time.Instant;
import java.util.List;

public interface LoginUseCase {

    LoginResult login(LoginCommand command);

    /**
     * @param email            user email
     * @param rawPassword      user password
     * @param householdId      optional — if present, an access token scoped to that
     *                         household is returned. If null, token is issued only
     *                         when the user has exactly one membership.
     */
    record LoginCommand(String email, String rawPassword, String householdId) {}

    /**
     * Always contains the list of memberships so the client can let the user pick a
     * household. accessToken is present only when a single household could be
     * unambiguously chosen (user supplied householdId, or has exactly one membership).
     */
    record LoginResult(String userId,
                       List<Membership> memberships,
                       String accessToken,
                       Instant expiresAt,
                       String activeHouseholdId,
                       String activeRole) {}

    record Membership(String householdId, String householdName, String role) {}
}