package com.misourav.homeorganizer.application.port.out;

import com.misourav.homeorganizer.domain.model.Household;
import com.misourav.homeorganizer.domain.model.HouseholdId;

import java.util.Optional;

public interface HouseholdRepository {
    Household save(Household household);
    Optional<Household> findById(HouseholdId id);
}
