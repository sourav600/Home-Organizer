package com.misourav.homeorganizer.adapter.out.persistence.adapter;

import com.misourav.homeorganizer.adapter.out.persistence.mapper.RolePersistenceMapper;
import com.misourav.homeorganizer.adapter.out.persistence.repository.RoleJpaRepository;
import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.RoleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository roleJpa;

    @Override
    public Role save(Role role) {
        return RolePersistenceMapper.toDomain(
                roleJpa.save(RolePersistenceMapper.toEntity(role))
        );
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return roleJpa.findById(id.value()).map(RolePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Role> findByCode(RoleCode code) {
        return roleJpa.findByCode(code).map(RolePersistenceMapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return roleJpa.findAll().stream().map(RolePersistenceMapper::toDomain).toList();
    }

    @Override
    public List<Role> findSelfAndDescendants(RoleId rootId) {
        return roleJpa.findSelfAndDescendants(rootId.value())
                .stream()
                .map(RolePersistenceMapper::toDomain)
                .toList();
    }
}
