package com.misourav.homeorganizer.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String ref) {
        super("User not found: " + ref);
    }
}
