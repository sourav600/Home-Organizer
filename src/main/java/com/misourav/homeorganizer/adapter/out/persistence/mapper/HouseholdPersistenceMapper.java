package com.misourav.homeorganizer.adapter.out.persistence.mapper;

import com.misourav.homeorganizer.adapter.out.persistence.entity.HouseholdJpaEntity;
import com.misourav.homeorganizer.domain.model.Household;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.UserId;

public final class HouseholdPersistenceMapper {

    private HouseholdPersistenceMapper() {}

    public static Household toDomain(HouseholdJpaEntity e) {
        return new Household(
                HouseholdId.of(e.getId()),
                e.getName(),
                UserId.of(e.getCreatedBy()),
                e.getCreatedAt()
        );
    }

    public static HouseholdJpaEntity toEntity(Household h) {
        return new HouseholdJpaEntity(
                h.id().value(),
                h.name(),
                h.createdBy().value(),
                h.createdAt()
        );
    }
}
