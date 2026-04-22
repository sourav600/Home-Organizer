package com.misourav.homeorganizer.adapter.out.persistence.mapper;

import com.misourav.homeorganizer.adapter.out.persistence.entity.HouseholdMemberJpaEntity;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.HouseholdMemberId;
import com.misourav.homeorganizer.domain.model.RoleId;
import com.misourav.homeorganizer.domain.model.UserId;

public final class HouseholdMemberPersistenceMapper {

    private HouseholdMemberPersistenceMapper() {}

    public static HouseholdMember toDomain(HouseholdMemberJpaEntity e) {
        return new HouseholdMember(
                HouseholdMemberId.of(e.getId()),
                UserId.of(e.getUserId()),
                HouseholdId.of(e.getHouseholdId()),
                RoleId.of(e.getRoleId()),
                e.getJoinedAt()
        );
    }

    public static HouseholdMemberJpaEntity toEntity(HouseholdMember m) {
        return new HouseholdMemberJpaEntity(
                m.id().value(),
                m.userId().value(),
                m.householdId().value(),
                m.roleId().value(),
                m.joinedAt()
        );
    }
}
