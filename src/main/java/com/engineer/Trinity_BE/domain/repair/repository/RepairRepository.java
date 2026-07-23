package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RepairRepository extends JpaRepository<Repair, Long>, RepairRepositoryCustom {

    // 무한스크롤 1단계: 커서 기반으로 이번 페이지의 Repair(연관 없이) 조회
    @Query("""
            select distinct r
            from Repair r
            join r.repairLocationItems rli
            join rli.repairLocation rl
            join rl.repairChapter rc
            where r.airplane.id = :airplaneId
              and r.deletedAt is null
              and rl.isActive = true
              and rc.isActive = true
              and (:cursorRepairAt is null
                   or r.repairAt < :cursorRepairAt
                   or (r.repairAt = :cursorRepairAt and r.id < :cursorId))
            order by r.repairAt desc, r.id desc
            """)
    List<Repair> findPageByAirplaneId(
            @Param("airplaneId") Long airplaneId,
            @Param("cursorRepairAt") LocalDateTime cursorRepairAt,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 무한스크롤 2단계: 확정된 id들로 fetch join하여 연관 데이터까지 로딩
    @Query("""
            select distinct r
            from Repair r
            left join fetch r.repairLocationItems rli
            left join fetch rli.repairLocation rl
            left join fetch rl.repairChapter rc
            where r.id in :ids
              and rl.isActive = true
              and rc.isActive = true
            """)
    List<Repair> findAllWithLocationsByIds(@Param("ids") List<Long> ids);

    @Query("""
            select distinct r
            from Repair r
            left join fetch r.repairLocationItems rli
            left join fetch rli.repairLocation rl
            left join fetch rl.repairChapter rc
            where r.airplane.id = :airplaneId
              and r.deletedAt is null
              and rl.isActive = true
              and rc.isActive = true
            order by r.repairAt desc
            """)
    List<Repair> findAllWithLocations(Long airplaneId);

    @Query("""
            select distinct r
            from Repair r
            left join fetch r.repairLocationItems rli
            left join fetch rli.repairLocation rl
            left join fetch rl.repairChapter rc
            where r.id = :repairId
              and r.deletedAt is null
              and rl.isActive = true
              and rc.isActive = true
            """)
    Optional<Repair> findDetail(Long repairId);

    Optional<Repair> findByIdAndUserId(Long id, Long userId);
}
