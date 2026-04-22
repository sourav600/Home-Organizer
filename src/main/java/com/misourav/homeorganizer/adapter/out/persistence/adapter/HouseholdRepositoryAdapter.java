package com.misourav.homeorganizer.adapter.out.persistence.adapter;

import com.misourav.homeorganizer.adapter.out.persistence.mapper.HouseholdPersistenceMapper;
import com.misourav.homeorganizer.adapter.out.persistence.repository.HouseholdJpaRepository;
import com.misourav.homeorganizer.application.port.out.HouseholdRepository;
import com.misourav.homeorganizer.domain.model.Household;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HouseholdRepositoryAdapter implements HouseholdRepository {

    private final HouseholdJpaRepository householdJpa;

    @Override
    public Household save(Household household) {
        return HouseholdPersistenceMapper.toDomain(
                householdJpa.save(HouseholdPersistenceMapper.toEntity(household))
        );
    }

    @Override
    public Optional<Household> findById(HouseholdId id) {
        return householdJpa.findById(id.value()).map(HouseholdPersistenceMapper::toDomain);
    }
}
