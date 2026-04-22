package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.VerifyEmailUseCase;
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
public class VerifyEmailService implements VerifyEmailUseCase {

    private final UserRepository userRepository;
    private final OtpService otpService;

    @Override
    @Transactional
    public void verify(VerifyEmailCommand cmd) {
        Email email = Email.of(cmd.email());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email.value()));

        if (user.isEmailVerified()) {
            throw new EmailAlreadyVerifiedException();
        }

        otpService.verifyOtp(email, cmd.otpCode());
        userRepository.save(user.verifyEmail());
    }
}
