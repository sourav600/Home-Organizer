package com.misourav.homeorganizer.domain.model;

import java.util.UUID;

public record HouseholdId(UUID value) {
    public HouseholdId {
        if (value == null) throw new IllegalArgumentException("HouseholdId value cannot be null");
    }

    public static HouseholdId newId() {
        return new HouseholdId(UUID.randomUUID());
    }

    public static HouseholdId of(UUID value) {
        return new HouseholdId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
