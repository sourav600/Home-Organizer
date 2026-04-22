package com.misourav.homeorganizer.domain.exception;

public class InvalidOtpException extends DomainException {
    public InvalidOtpException() {
        super("Invalid or expired OTP");
    }
}
