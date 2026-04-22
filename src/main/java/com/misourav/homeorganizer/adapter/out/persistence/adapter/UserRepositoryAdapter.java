package com.misourav.homeorganizer.adapter.out.persistence.adapter;

import com.misourav.homeorganizer.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.misourav.homeorganizer.adapter.out.persistence.repository.UserJpaRepository;
import com.misourav.homeorganizer.application.port.out.UserRepository;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.User;
import com.misourav.homeorganizer.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpa;

    @Override
    public User save(User user) {
        return UserPersistenceMapper.toDomain(
                userJpa.save(UserPersistenceMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpa.findById(id.value()).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return userJpa.findByEmail(email.value()).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userJpa.existsByEmail(email.value());
    }
}
