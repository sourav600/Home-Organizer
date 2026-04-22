package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface HouseholdMemberRepository {

    HouseholdMember save(HouseholdMember member);

    Optional<HouseholdMember> findByUserAndHousehold(UserId userId, HouseholdId householdId);

    List<HouseholdMember> findAllByUser(UserId userId);

    boolean existsByUserAndHousehold(UserId userId, HouseholdId householdId);
}