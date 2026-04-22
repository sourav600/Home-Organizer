package com.misourav.homeorganizer.adapter.out.persistence.repository;

import com.misourav.homeorganizer.adapter.out.persistence.entity.HouseholdMemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseholdMemberJpaRepository
        extends JpaRepository<HouseholdMemberJpaEntity, UUID> {

    Optional<HouseholdMemberJpaEntity> findByUserIdAndHouseholdId(UUID userId, UUID householdId);

    List<HouseholdMemberJpaEntity> findAllByUserId(UUID userId);

    boolean existsByUserIdAndHouseholdId(UUID userId, UUID householdId);
}
