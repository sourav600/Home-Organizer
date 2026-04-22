package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.HashedPassword;

public interface PasswordHasher {
    HashedPassword hash(String rawPassword);
    boolean matches(String rawPassword, HashedPassword hash);
}
