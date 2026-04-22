package com.misourav.homeorganizer.application.port.in;

public interface VerifyEmailUseCase {

    void verify(VerifyEmailCommand command);

    record VerifyEmailCommand(String email, String otpCode) {}
}
