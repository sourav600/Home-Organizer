package com.misourav.homeorganizer.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "household_members",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_member_user_household",
                columnNames = {"user_id", "household_id"}),
        indexes = {
                @Index(name = "idx_member_user", columnList = "user_id"),
                @Index(name = "idx_member_household", columnList = "household_id")
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HouseholdMemberJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "household_id", nullable = false)
    private UUID householdId;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;
}
