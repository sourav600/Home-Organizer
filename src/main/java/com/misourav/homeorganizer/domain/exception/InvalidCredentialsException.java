package com.misourav.homeorganizer.domain.exception;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
