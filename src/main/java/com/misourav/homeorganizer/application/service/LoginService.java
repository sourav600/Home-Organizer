package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.LoginUseCase;
import com.misourav.homeorganizer.application.port.out.HouseholdMemberRepository;
import com.misourav.homeorganizer.application.port.out.HouseholdRepository;
import com.misourav.homeorganizer.application.port.out.PasswordHasher;
import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.application.port.out.TokenProvider;
import com.misourav.homeorganizer.application.port.out.UserRepository;
import com.misourav.homeorganizer.domain.exception.InvalidCredentialsException;
import com.misourav.homeorganizer.domain.exception.NotAMemberException;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.Household;
import com.misourav.homeorganizer.domain.model.HouseholdId;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository memberRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public LoginResult login(LoginCommand cmd) {
        Email email;
        try {
            email = Email.of(cmd.email());
        } catch (IllegalArgumentException e) {
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive() || !passwordHasher.matches(cmd.rawPassword(), user.passwordHash())) {
            throw new InvalidCredentialsException();
        }

        List<HouseholdMember> memberships = memberRepository.findAllByUser(user.id());
        List<Membership> membershipViews = buildMembershipViews(memberships);

        // Decide whether to issue a token right away.
        HouseholdMember active = resolveActiveMembership(memberships, cmd.householdId(), user.id());

        if (active == null) {
            return new LoginResult(user.id().toString(), membershipViews,
                    null, null, null, null);
        }

        Role role = roleRepository.findById(active.roleId())
                .orElseThrow(InvalidCredentialsException::new);

        TokenProvider.IssuedToken issued =
                tokenProvider.issue(user.id(), role.code(), active.householdId());

        return new LoginResult(
                user.id().toString(),
                membershipViews,
                issued.token(),
                issued.expiresAt(),
                active.householdId().toString(),
                role.code().name());
    }

    private List<Membership> buildMembershipViews(List<HouseholdMember> memberships) {
        List<Membership> out = new ArrayList<>(memberships.size());
        for (HouseholdMember m : memberships) {
            Household household = householdRepository.findById(m.householdId()).orElse(null);
            Role role = roleRepository.findById(m.roleId()).orElse(null);
            out.add(new Membership(
                    m.householdId().toString(),
                    household == null ? "" : household.name(),
                    role == null ? "" : role.code().name()
            ));
        }
        return out;
    }

    private HouseholdMember resolveActiveMembership(List<HouseholdMember> memberships,
                                                    String requestedHouseholdId,
                                                    com.misourav.homeorganizer.domain.model.UserId userId) {
        if (requestedHouseholdId != null && !requestedHouseholdId.isBlank()) {
            HouseholdId targetId = HouseholdId.of(UUID.fromString(requestedHouseholdId));
            return memberships.stream()
                    .filter(m -> m.householdId().equals(targetId))
                    .findFirst()
                    .orElseThrow(() -> new NotAMemberException(userId.toString(), requestedHouseholdId));
        }
        if (memberships.size() == 1) {
            return memberships.get(0);
        }
        return null;
    }
}