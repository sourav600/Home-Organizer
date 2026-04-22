package com.misourav.homeorganizer.domain.model;

import java.util.Objects;
import java.util.UUID;

public record OtpTokenId(UUID value) {
    public OtpTokenId { Objects.requireNonNull(value); }
    public static OtpTokenId newId() { return new OtpTokenId(UUID.randomUUID()); }
    public static OtpTokenId of(UUID v) { return new OtpTokenId(v); }
    @Override public String toString() { return value.toString(); }
}
