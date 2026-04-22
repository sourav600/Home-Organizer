package com.misourav.homeorganizer.domain.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * One-time password issued against an email address. Immutable: {@link #markUsed()}
 * returns a new instance rather than mutating the current one.
 */
public final class OtpToken {

    private final OtpTokenId id;
    private final Email email;
    private final String code;
    private final Instant expiresAt;
    @Getter
    private final boolean used;
    private final Instant createdAt;

    public OtpToken(OtpTokenId id,
                    Email email,
                    String code,
                    Instant expiresAt,
                    boolean used,
                    Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.code = Objects.requireNonNull(code);
        this.expiresAt = Objects.requireNonNull(expiresAt);
        this.used = used;
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public static OtpToken issue(Email email, String code, Duration ttl) {
        Instant now = Instant.now();
        return new OtpToken(OtpTokenId.newId(), email, code, now.plus(ttl), false, now);
    }

    public OtpTokenId id() { return id; }
    public Email email() { return email; }
    public String code() { return code; }
    public Instant expiresAt() { return expiresAt; }
    public Instant createdAt() { return createdAt; }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public OtpToken markUsed() {
        return new OtpToken(id, email, code, expiresAt, true, createdAt);
    }
}
