package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.out.OtpTokenRepository;
import com.misourav.homeorganizer.domain.exception.InvalidOtpException;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.OtpToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;

/**
 * Generates and verifies OTPs. Callers are responsible for sending the returned
 * code via {@link com.misourav.homeorganizer.application.port.out.EmailSender}.
 */
@Service
@Slf4j
public class OtpService {

    private static final String DIGITS = "0123456789";
    private final SecureRandom random = new SecureRandom();

    private final OtpTokenRepository otpTokenRepository;
    private final int otpLength;
    private final Duration ttl;

    public OtpService(OtpTokenRepository otpTokenRepository,
                      @Value("${app.otp.length:6}") int otpLength,
                      @Value("${app.otp.expiry-minutes:5}") int expiryMinutes) {
        this.otpTokenRepository = otpTokenRepository;
        this.otpLength = otpLength;
        this.ttl = Duration.ofMinutes(expiryMinutes);
    }

    @Transactional
    public String generateAndSaveOtp(Email email) {
        otpTokenRepository.deleteByEmail(email);
        String code = generateCode();
        OtpToken token = OtpToken.issue(email, code, ttl);
        otpTokenRepository.save(token);
        log.info("OTP generated for email: {}", email.value());
        return code;
    }

    /**
     * Atomically verify and consume an OTP. Throws {@link InvalidOtpException} if
     * the code is unknown, already used, or expired.
     */
    @Transactional
    public void verifyOtp(Email email, String code) {
        OtpToken token = otpTokenRepository.findActiveByEmailAndCode(email, code)
                .orElseThrow(InvalidOtpException::new);

        if (token.isExpired()) {
            log.warn("OTP verification failed - expired OTP for email: {}", email.value());
            throw new InvalidOtpException();
        }

        otpTokenRepository.save(token.markUsed());
        log.info("OTP verified successfully for email: {}", email.value());
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return sb.toString();
    }
}
