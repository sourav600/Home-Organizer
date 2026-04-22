package com.misourav.homeorganizer.adapter.out.persistence.adapter;

import com.misourav.homeorganizer.adapter.out.persistence.mapper.HouseholdMemberPersistenceMapper;
import com.misourav.homeorganizer.adapter.out.persistence.repository.HouseholdMemberJpaRepository;
import com.misourav.homeorganizer.application.port.out.HouseholdMemberRepository;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HouseholdMemberRepositoryAdapter implements HouseholdMemberRepository {

    private final HouseholdMemberJpaRepository jpa;

    @Override
    public HouseholdMember save(HouseholdMember member) {
        return HouseholdMemberPersistenceMapper.toDomain(
                jpa.save(HouseholdMemberPersistenceMapper.toEntity(member)));
    }

    @Override
    public Optional<HouseholdMember> findByUserAndHousehold(UserId userId, HouseholdId householdId) {
        return jpa.findByUserIdAndHouseholdId(userId.value(), householdId.value())
                .map(HouseholdMemberPersistenceMapper::toDomain);
    }

    @Override
    public List<HouseholdMember> findAllByUser(UserId userId) {
        return jpa.findAllByUserId(userId.value()).stream()
                .map(HouseholdMemberPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByUserAndHousehold(UserId userId, HouseholdId householdId) {
        return jpa.existsByUserIdAndHouseholdId(userId.value(), householdId.value());
    }
}
