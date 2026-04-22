package com.misourav.homeorganizer.domain.exception;

public class EmailAlreadyVerifiedException extends DomainException {
    public EmailAlreadyVerifiedException() {
        super("Email already verified");
    }
}
