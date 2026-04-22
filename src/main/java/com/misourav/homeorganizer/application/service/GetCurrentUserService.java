package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.GetCurrentUserQuery;
import com.misourav.homeorganizer.application.port.out.HouseholdMemberRepository;
import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.application.port.out.UserRepository;
import com.misourav.homeorganizer.domain.exception.NotAMemberException;
import com.misourav.homeorganizer.domain.exception.UserNotFoundException;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.PermissionCode;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.User;
import com.misourav.homeorganizer.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCurrentUserService implements GetCurrentUserQuery {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HouseholdMemberRepository memberRepository;
    private final PermissionResolver permissionResolver;

    @Override
    @Transactional(readOnly = true)
    public CurrentUserView get(UserId userId, HouseholdId householdId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        HouseholdMember membership = memberRepository
                .findByUserAndHousehold(userId, householdId)
                .orElseThrow(() -> new NotAMemberException(userId.toString(), householdId.toString()));

        Role role = roleRepository.findById(membership.roleId())
                .orElseThrow(() -> new UserNotFoundException("role of " + userId));

        Set<PermissionCode> perms = permissionResolver.resolveByRoleId(role.id());

        return new CurrentUserView(
                user.id().toString(),
                user.email().value(),
                user.name(),
                role.code().name(),
                householdId.toString(),
                perms.stream().map(Enum::name).collect(Collectors.toUnmodifiableSet())
        );
    }
}