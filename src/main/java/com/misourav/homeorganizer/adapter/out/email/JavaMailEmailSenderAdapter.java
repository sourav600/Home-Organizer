package com.misourav.homeorganizer.adapter.out.email;

import com.misourav.homeorganizer.application.port.out.EmailSender;
import com.misourav.homeorganizer.domain.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JavaMailEmailSenderAdapter implements EmailSender {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    @Async
    public void sendOtpEmail(Email to, String otpCode, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to.value());
            message.setFrom(mailProperties.from());
            message.setSubject("Email Verification - OTP Code");
            message.setText(buildBody(otpCode, userName));
            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", to.value());
        } catch (Exception e) {
            // Intentionally do not rethrow: method runs @Async, so the caller's
            // transaction has already committed. Failures are logged for ops.
            log.error("Failed to send OTP email to: {}", to.value(), e);
        }
    }

    private String buildBody(String otpCode, String userName) {
        return String.format(
                "Dear %s,%n%n" +
                        "Thank you for registering with Home Organizer!%n%n" +
                        "Your One-Time Password (OTP) for email verification is: %s%n%n" +
                        "This OTP is valid for 5 minutes only. Please do not share this code " +
                        "with anyone.%n%n" +
                        "If you did not request this registration, please ignore this email.%n%n" +
                        "Best regards,%n" +
                        "Home Organizer Team",
                userName, otpCode);
    }

    @Configuration
    @EnableConfigurationProperties(MailProperties.class)
    static class MailConfig {}
}
