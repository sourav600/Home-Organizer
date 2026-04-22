package com.misourav.homeorganizer.adapter.out.persistence.repository;

import com.misourav.homeorganizer.adapter.out.persistence.entity.OtpTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OtpTokenJpaRepository extends JpaRepository<OtpTokenJpaEntity, UUID> {

    Optional<OtpTokenJpaEntity> findFirstByEmailAndCodeAndUsedFalseOrderByCreatedAtDesc(
            String email, String code);

    @Modifying
    @Query("delete from OtpTokenJpaEntity o where o.email = :email")
    void deleteByEmail(@Param("email") String email);
}
