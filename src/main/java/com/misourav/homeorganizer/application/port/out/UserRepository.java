package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.User;
import com.misourav.homeorganizer.domain.model.UserId;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);
}
