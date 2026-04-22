package com.misourav.homeorganizer.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Ties a User to a Household with a specific Role.
 * The SAME user can hold a different role in different households:
 * (alice, home-A, HOUSEHOLDER)
 * (alice, home-B, MEMBER)
 * Uniqueness: (userId, householdId) — one role per user per household.
 */
public record HouseholdMember(HouseholdMemberId id, UserId userId, HouseholdId householdId, RoleId roleId,
                              Instant joinedAt) {

    public HouseholdMember(HouseholdMemberId id,
                           UserId userId,
                           HouseholdId householdId,
                           RoleId roleId,
                           Instant joinedAt) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.householdId = Objects.requireNonNull(householdId);
        this.roleId = Objects.requireNonNull(roleId);
        this.joinedAt = Objects.requireNonNull(joinedAt);
    }

    public static HouseholdMember join(UserId userId, HouseholdId householdId, RoleId roleId) {
        return new HouseholdMember(
                HouseholdMemberId.newId(), userId, householdId, roleId, Instant.now());
    }

    public HouseholdMember changeRole(RoleId newRoleId) {
        return new HouseholdMember(id, userId, householdId, newRoleId, joinedAt);
    }
}
