package com.misourav.homeorganizer.config.bootstrap;

import com.misourav.homeorganizer.application.port.out.RoleRepository;
import com.misourav.homeorganizer.domain.model.PermissionCode;
import com.misourav.homeorganizer.domain.model.Role;
import com.misourav.homeorganizer.domain.model.RoleCode;
import com.misourav.homeorganizer.domain.model.RoleId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Set;

/**
 * Seeds the four built-in roles and their direct permissions on application start.
 * Hierarchy:
 *   HOUSEHOLDER -> HOUSEMAKER -> MEMBER -> CHILD
 * Inheritance is resolved at runtime by PermissionResolver, so each role here stores
 * only the permissions that are unique to that level.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RoleSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        if (roleRepository.findByCode(RoleCode.HOUSEHOLDER).isPresent()) {
            log.info("Roles already seeded — skipping");
            return;
        }

        Set<PermissionCode> childPerms = EnumSet.of(
                PermissionCode.MEAL_ENTER_SELF,
                PermissionCode.TASK_COMPLETE_OWN,
                PermissionCode.TASK_VIEW,
                PermissionCode.CHAT_SEND
        );

        Set<PermissionCode> memberPerms = EnumSet.of(
                PermissionCode.EXPENSE_CREATE_OWN,
                PermissionCode.EXPENSE_VIEW_OWN,
                PermissionCode.TASK_CREATE,
                PermissionCode.TASK_ASSIGN,
                PermissionCode.BILL_VIEW,
                PermissionCode.REPORT_VIEW_OWN
        );

        Set<PermissionCode> housemakerPerms = EnumSet.of(
                PermissionCode.EXPENSE_CREATE_ANY,
                PermissionCode.EXPENSE_EDIT_ANY,
                PermissionCode.EXPENSE_VIEW_ANY,
                PermissionCode.BAZAAR_ENTER,
                PermissionCode.MEAL_CLOSE,
                PermissionCode.MEAL_ENTER_ANY,
                PermissionCode.BILL_CREATE,
                PermissionCode.SHOPPING_MANAGE,
                PermissionCode.REPORT_VIEW_HOUSEHOLD,
                PermissionCode.ANNOUNCEMENT_POST,
                PermissionCode.TASK_COMPLETE_ANY
        );

        Set<PermissionCode> householderPerms = EnumSet.of(
                PermissionCode.MEMBER_INVITE,
                PermissionCode.MEMBER_REMOVE,
                PermissionCode.ROLE_CHANGE,
                PermissionCode.HOUSEHOLD_SETTINGS,
                PermissionCode.BUDGET_MANAGE,
                PermissionCode.EXPENSE_DELETE,
                PermissionCode.BILL_DELETE
        );

        Role child = new Role(RoleId.newId(), RoleCode.CHILD, "Child",
                null, true, childPerms);
        Role savedChild = roleRepository.save(child);

        Role member = new Role(RoleId.newId(), RoleCode.MEMBER, "Member",
                savedChild.id(), true, memberPerms);
        Role savedMember = roleRepository.save(member);

        Role housemaker = new Role(RoleId.newId(), RoleCode.HOUSEMAKER, "Housemaker",
                savedMember.id(), true, housemakerPerms);
        Role savedHousemaker = roleRepository.save(housemaker);

        Role householder = new Role(RoleId.newId(), RoleCode.HOUSEHOLDER, "Householder",
                savedHousemaker.id(), true, householderPerms);
        roleRepository.save(householder);

        log.info("Seeded 4 roles with hierarchy: HOUSEHOLDER -> HOUSEMAKER -> MEMBER -> CHILD");
    }
}
