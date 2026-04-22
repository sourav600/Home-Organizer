package com.misourav.homeorganizer.adapter.out.persistence.repository;

import com.misourav.homeorganizer.adapter.out.persistence.entity.HouseholdJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HouseholdJpaRepository extends JpaRepository<HouseholdJpaEntity, UUID> {
}
