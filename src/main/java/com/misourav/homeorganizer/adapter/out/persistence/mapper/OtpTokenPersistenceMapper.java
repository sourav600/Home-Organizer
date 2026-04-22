package com.misourav.homeorganizer.adapter.out.persistence.mapper;

import com.misourav.homeorganizer.adapter.out.persistence.entity.OtpTokenJpaEntity;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.OtpToken;
import com.misourav.homeorganizer.domain.model.OtpTokenId;

public final class OtpTokenPersistenceMapper {

    private OtpTokenPersistenceMapper() {}

    public static OtpToken toDomain(OtpTokenJpaEntity e) {
        return new OtpToken(
                OtpTokenId.of(e.getId()),
                Email.of(e.getEmail()),
                e.getCode(),
                e.getExpiresAt(),
                e.isUsed(),
                e.getCreatedAt()
        );
    }

    public static OtpTokenJpaEntity toEntity(OtpToken t) {
        return new OtpTokenJpaEntity(
                t.id().value(),
                t.email().value(),
                t.code(),
                t.expiresAt(),
                t.isUsed(),
                t.createdAt()
        );
    }
}
