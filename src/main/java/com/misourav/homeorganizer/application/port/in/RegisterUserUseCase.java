package com.misourav.homeorganizer.application.port.in;

import com.misourav.homeorganizer.domain.model.UserId;

public interface RegisterUserUseCase {

    UserId register(RegisterUserCommand command);

    record RegisterUserCommand(String email,
                               String name,
                               String rawPassword,
                               String householdName) {}
}
