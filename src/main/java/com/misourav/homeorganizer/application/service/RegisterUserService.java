package com.misourav.homeorganizer.application.service;

import com.misourav.homeorganizer.application.port.in.RegisterUserUseCase;
import com.misourav.homeorganizer.application.port.out.HouseholdMemberRepository;
import com.misourav.homeorganizer.application.port.out.HouseholdRepository;
import com.misourav.homeorganizer.application.port.out.PasswordHasher;
import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.application.port.out.UserRepository;
import com.misourav.homeorganizer.domain.exception.EmailAlreadyUsedException;
import com.misourav.homeorganizer.domain.exception.RoleNotFoundException;
import com.misourav.homeorganizer.domain.model.Email;
import com.misourav.homeorganizer.domain.model.HashedPassword;
import com.misourav.homeorganizer.domain.model.Household;
import com.misourav.homeorganizer.domain.model.HouseholdMember;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.User;
import com.misourav.homeorganizer.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registers a new user and creates their first household. The registering user
 * joins that household as HOUSEHOLDER via a HouseholdMember link.
 * Later, "invite" / "accept invite" use cases will create additional HouseholdMember
 * rows for the same user — possibly with a different role in a different household.
 */
@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository memberRepository;
    private final PasswordHasher passwordHasher;

    @Override
    @Transactional
    public UserId register(RegisterUserCommand cmd) {
        Email email = Email.of(cmd.email());

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException(email.value());
        }

        Role householderRole = roleRepository.findByCode(RoleCode.HOUSEHOLDER)
                .orElseThrow(() -> new RoleNotFoundException(RoleCode.HOUSEHOLDER));

        HashedPassword hash = passwordHasher.hash(cmd.rawPassword());
        User user = User.newUser(email, cmd.name(), hash);
        User savedUser = userRepository.save(user);

        Household household = Household.create(cmd.householdName(), savedUser.id());
        Household savedHousehold = householdRepository.save(household);

        HouseholdMember membership = HouseholdMember.join(
                savedUser.id(), savedHousehold.id(), householderRole.id());
        memberRepository.save(membership);

        return savedUser.id();
    }
}