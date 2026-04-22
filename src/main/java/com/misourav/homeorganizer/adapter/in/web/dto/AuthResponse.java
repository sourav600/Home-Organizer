package com.misourav.homeorganizer.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;

/**
 * Login response. When {@code accessToken} is null the client must pick a household
 * and retry login with it (or call /auth/switch-household if already authenticated).
 */
public record AuthResponse(String userId,
                           List<MembershipDto> memberships,
                           String accessToken,
                           String tokenType,
                           Instant expiresAt,
                           String activeHouseholdId,
                           String activeRole) {

    public record MembershipDto(String householdId, String householdName, String role) {}
}
