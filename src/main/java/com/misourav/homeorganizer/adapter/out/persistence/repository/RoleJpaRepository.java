package com.misourav.homeorganizer.adapter.out.persistence.repository;

import com.misourav.homeorganizer.adapter.out.persistence.entity.RoleJpaEntity;
import com.misourav.homeorganizer.domain.model.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, UUID> {

    Optional<RoleJpaEntity> findByCode(RoleCode code);

    /**
     * Recursively fetch the given role plus all descendants via the
     * parent_id self-reference. Uses a CTE — works on H2 (PG mode),
     * PostgreSQL and most modern RDBMS.
     */
    @Query(value = """
            WITH RECURSIVE role_tree AS (
                SELECT id FROM roles WHERE id = :rootId
                UNION ALL
                SELECT r.id FROM roles r
                JOIN role_tree rt ON r.parent_id = rt.id
            )
            SELECT r.* FROM roles r
            WHERE r.id IN (SELECT id FROM role_tree)
            """, nativeQuery = true)
    List<RoleJpaEntity> findSelfAndDescendants(@Param("rootId") UUID rootId);
}
