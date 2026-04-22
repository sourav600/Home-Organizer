package com.misourav.homeorganizer.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * User aggregate root. A User is an identity; their role is NOT stored here —
 * roles live on HouseholdMember, so the same user can be HOUSEHOLDER in their own
 * home and MEMBER in their parents' home.
 */
public final class User {
    private final UserId id;
    private final Email email;
    private final String name;
    private final HashedPassword passwordHash;
    @Getter
    private final boolean active;
    @Getter
    private final boolean emailVerified;
    private final Instant createdAt;

    public User(UserId id,
                Email email,
                String name,
                HashedPassword passwordHash,
                boolean active,
                boolean emailVerified,
                Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.name = Objects.requireNonNull(name);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.active = active;
        this.emailVerified = emailVerified;
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static User newUser(Email email, String name, HashedPassword passwordHash) {
        return new User(UserId.newId(), email, name, passwordHash, true, false, Instant.now());
    }

    public UserId id() { return id; }
    public Email email() { return email; }
    public String name() { return name; }
    public HashedPassword passwordHash() { return passwordHash; }
    public Instant createdAt() { return createdAt; }

    public User deactivate() {
        return new User(id, email, name, passwordHash, false, emailVerified, createdAt);
    }

    public User verifyEmail() {
        return new User(id, email, name, passwordHash, active, true, createdAt);
    }
}
