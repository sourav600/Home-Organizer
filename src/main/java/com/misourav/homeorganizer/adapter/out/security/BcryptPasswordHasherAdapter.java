package com.misourav.homeorganizer.adapter.out.security;

import com.misourav.homeorganizer.application.port.out.PasswordHasher;
import com.misourav.homeorganizer.domain.model.HashedPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordHasherAdapter implements PasswordHasher {

    private final PasswordEncoder encoder;

    @Override
    public HashedPassword hash(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        return HashedPassword.of(encoder.encode(rawPassword));
    }

    @Override
    public boolean matches(String rawPassword, HashedPassword hash) {
        if (rawPassword == null || hash == null) return false;
        return encoder.matches(rawPassword, hash.value());
    }
}
