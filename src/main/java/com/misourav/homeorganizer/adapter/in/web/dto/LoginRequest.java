package com.misourav.homeorganizer.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * householdId is optional. If present, the server issues an access token scoped to
 * that household. If omitted and the user has exactly one membership, a token is
 * also issued. Otherwise the response contains memberships only and the client
 * must POST again with the chosen householdId.
 */
public record LoginRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        String householdId
) {}
