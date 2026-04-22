package com.misourav.homeorganizer.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OtpVerificationRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 4, max = 10) String otpCode
) {}
