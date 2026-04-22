package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.Email;

public interface EmailSender {

    void sendOtpEmail(Email to, String otpCode, String userName);
}
