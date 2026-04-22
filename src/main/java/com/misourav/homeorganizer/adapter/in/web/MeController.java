package com.misourav.homeorganizer.adapter.in.web;

import com.misourav.homeorganizer.adapter.in.web.dto.MeResponse;
import com.misourav.homeorganizer.application.port.in.GetCurrentUserQuery;
import com.misourav.homeorganizer.config.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final GetCurrentUserQuery getCurrentUserQuery;

    @GetMapping
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal AuthenticatedUser principal) {
        if (principal.householdId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var v = getCurrentUserQuery.get(principal.userId(), principal.householdId());
        return ResponseEntity.ok(new MeResponse(
                v.userId(), v.email(), v.name(), v.role(),
                v.householdId(), v.permissions()));
    }
}
