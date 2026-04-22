package com.misourav.homeorganizer.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendOtpRequest(@Email @NotBlank String email) {}
