package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.SwitchHouseholdUseCase;
import com.misourav.homeorganizer.application.port.out.HouseholdMemberRepository;
import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.application.port.out.TokenProvider;
import com.misourav.homeorganizer.domain.exception.NotAMemberException;
import com.misourav.homeorganizer.domain.exception.RoleNotFoundException;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SwitchHouseholdService implements SwitchHouseholdUseCase {

    private final HouseholdMemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public SwitchResult switchTo(UserId userId, HouseholdId targetHouseholdId) {
        HouseholdMember membership = memberRepository
                .findByUserAndHousehold(userId, targetHouseholdId)
                .orElseThrow(() -> new NotAMemberException(
                        userId.toString(), targetHouseholdId.toString()));

        Role role = roleRepository.findById(membership.roleId())
                .orElseThrow(() -> new RoleNotFoundException(null));

        TokenProvider.IssuedToken issued =
                tokenProvider.issue(userId, role.code(), targetHouseholdId);

        return new SwitchResult(
                issued.token(),
                issued.expiresAt(),
                targetHouseholdId.toString(),
                role.code().name());
    }
}
