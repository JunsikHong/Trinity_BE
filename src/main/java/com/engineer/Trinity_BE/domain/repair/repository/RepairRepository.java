package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepairRepository extends JpaRepository<Repair, Long> {

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
