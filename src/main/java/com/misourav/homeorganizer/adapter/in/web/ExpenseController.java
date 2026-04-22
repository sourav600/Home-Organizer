package com.misourav.homeorganizer.adapter.in.web;

import com.misourav.homeorganizer.config.security.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * Demo endpoints showing permission enforcement via @PreAuthorize.
 * Authorities carried on the SecurityContext are permission codes from the
 * user's role hierarchy. Replace the in-memory stubs with real service calls
 * when the Expense use cases are implemented.
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EXPENSE_VIEW_OWN','EXPENSE_VIEW_ANY')")
    public Map<String, Object> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return Map.of(
                "householdId", user.householdId() == null ? null : user.householdId().toString(),
                "expenses", java.util.List.of());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EXPENSE_CREATE_OWN','EXPENSE_CREATE_ANY')")
    public ResponseEntity<Map<String, String>> create(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.status(201).body(Map.of("id", UUID.randomUUID().toString()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXPENSE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id,
                                       @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.noContent().build();
    }
}
