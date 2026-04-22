package com.misourav.homeorganizer.adapter.in.web;

import com.misourav.homeorganizer.adapter.in.web.dto.AuthResponse;
import com.misourav.homeorganizer.adapter.in.web.dto.LoginRequest;
import com.misourav.homeorganizer.adapter.in.web.dto.RegisterRequest;
import com.misourav.homeorganizer.application.port.in.LoginUseCase;
import com.misourav.homeorganizer.application.port.in.RegisterUserUseCase;
import com.misourav.homeorganizer.application.port.in.SwitchHouseholdUseCase;
import com.misourav.homeorganizer.config.security.AuthenticatedUser;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final SwitchHouseholdUseCase switchHouseholdUseCase;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest req) {
        UserId id = registerUserUseCase.register(
                new RegisterUserUseCase.RegisterUserCommand(
                        req.email(), req.name(), req.password(), req.householdName()));
        return ResponseEntity.status(201).body(Map.of("userId", id.toString()));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        var result = loginUseCase.login(new LoginUseCase.LoginCommand(
                req.email(), req.password(), req.householdId()));

        List<AuthResponse.MembershipDto> memberships = result.memberships().stream()
                .map(m -> new AuthResponse.MembershipDto(
                        m.householdId(), m.householdName(), m.role()))
                .toList();

        return new AuthResponse(
                result.userId(),
                memberships,
                result.accessToken(),
                result.accessToken() == null ? null : "Bearer",
                result.expiresAt(),
                result.activeHouseholdId(),
                result.activeRole());
    }

    /**
     * Requires an already-valid JWT. Returns a new token scoped to the target household.
     */
    @PostMapping("/switch-household/{householdId}")
    public AuthResponse switchHousehold(@PathVariable UUID householdId,
                                        @AuthenticationPrincipal AuthenticatedUser principal) {
        var r = switchHouseholdUseCase.switchTo(
                principal.userId(), HouseholdId.of(householdId));

        return new AuthResponse(
                principal.userId().toString(),
                List.of(),
                r.accessToken(),
                "Bearer",
                r.expiresAt(),
                r.householdId(),
                r.role());
    }
}
