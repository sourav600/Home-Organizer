package com.misourav.homeorganizer.domain.model;

import java.time.Instant;
import java.util.Objects;

public final class Household {
    private final HouseholdId id;
    private final String name;
    private final UserId createdBy;
    private final Instant createdAt;

    public Household(HouseholdId id, String name, UserId createdBy, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.createdBy = Objects.requireNonNull(createdBy);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static Household create(String name, UserId createdBy) {
        return new Household(HouseholdId.newId(), name, createdBy, Instant.now());
    }

    public HouseholdId id() { return id; }
    public String name() { return name; }
    public UserId createdBy() { return createdBy; }
    public Instant createdAt() { return createdAt; }
}
