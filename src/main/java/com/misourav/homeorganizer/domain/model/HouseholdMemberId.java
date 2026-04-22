package com.misourav.homeorganizer.domain.model;

import java.util.UUID;

public record HouseholdMemberId(UUID value) {
    public HouseholdMemberId {
        if (value == null) throw new IllegalArgumentException("HouseholdMemberId value cannot be null");
    }

    public static HouseholdMemberId newId() {
        return new HouseholdMemberId(UUID.randomUUID());
    }

    public static HouseholdMemberId of(UUID value) {
        return new HouseholdMemberId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}