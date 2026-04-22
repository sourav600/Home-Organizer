package com.misourav.homeorganizer.adapter.out.persistence.adapter;

import com.misourav.homeorganizer.adapter.out.persistence.mapper.OtpTokenPersistenceMapper;
import com.misourav.homeorganizer.adapter.out.persistence.repository.OtpTokenJpaRepository;
import com.misourav.homeorganizer.application.port.out.OtpTokenRepository;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.OtpToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OtpTokenRepositoryAdapter implements OtpTokenRepository {

    private final OtpTokenJpaRepository jpa;

    @Override
    public OtpToken save(OtpToken token) {
        return OtpTokenPersistenceMapper.toDomain(
                jpa.save(OtpTokenPersistenceMapper.toEntity(token)));
    }

    @Override
    public Optional<OtpToken> findActiveByEmailAndCode(Email email, String code) {
        return jpa.findFirstByEmailAndCodeAndUsedFalseOrderByCreatedAtDesc(email.value(), code)
                .map(OtpTokenPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteByEmail(Email email) {
        jpa.deleteByEmail(email.value());
    }
}
