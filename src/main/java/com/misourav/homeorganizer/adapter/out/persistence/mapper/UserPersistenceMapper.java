package com.misourav.homeorganizer.adapter.out.persistence.mapper;

import com.misourav.homeorganizer.adapter.out.persistence.entity.UserJpaEntity;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.HashedPassword;
import com.misourav.homeorganizer.domain.model.User;
import com.misourav.homeorganizer.domain.model.UserId;

public final class UserPersistenceMapper {

    private UserPersistenceMapper() {}

    public static User toDomain(UserJpaEntity e) {
        return new User(
                UserId.of(e.getId()),
                Email.of(e.getEmail()),
                e.getName(),
                HashedPassword.of(e.getPasswordHash()),
                e.isActive(),
                e.isEmailVerified(),
                e.getCreatedAt()
        );
    }

    public static UserJpaEntity toEntity(User u) {
        return new UserJpaEntity(
                u.id().value(),
                u.email().value(),
                u.name(),
                u.passwordHash().value(),
                u.isActive(),
                u.isEmailVerified(),
                u.createdAt()
        );
    }
}
