package com.misourav.homeorganizer.domain.exception;

public class EmailNotVerifiedException extends DomainException {
    public EmailNotVerifiedException() {
        super("Email not verified. Please verify your email with the OTP sent to you.");
    }
}
