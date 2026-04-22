package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.OtpToken;

import java.util.Optional;

public interface OtpTokenRepository {

    OtpToken save(OtpToken token);

    Optional<OtpToken> findActiveByEmailAndCode(Email email, String code);

    void deleteByEmail(Email email);
}
