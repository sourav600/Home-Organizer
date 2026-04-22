package com.misourav.homeorganizer.adapter.in.web.dto;

import java.util.Set;

public record MeResponse(String userId,
                         String email,
                         String name,
                         String role,
                         String householdId,
                         Set<String> permissions) {
}
