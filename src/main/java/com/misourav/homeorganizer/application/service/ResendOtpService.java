package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.ResendOtpUseCase;
import com.misourav.homeorganizer.application.port.out.EmailSender;
import com.misourav.homeorganizer.application.port.out.UserRepository;
import com.misourav.homeorganizer.domain.exception.EmailAlreadyVerifiedException;
import com.misourav.homeorganizer.domain.exception.UserNotFoundException;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResendOtpService implements ResendOtpUseCase {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final EmailSender emailSender;

    @Override
    @Transactional
    public void resend(String rawEmail) {
        Email email = Email.of(rawEmail);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email.value()));

        if (user.isEmailVerified()) {
            throw new EmailAlreadyVerifiedException();
        }

        String code = otpService.generateAndSaveOtp(email);
        emailSender.sendOtpEmail(email, code, user.name());
    }
}
