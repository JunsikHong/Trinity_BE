package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepairLocationRepository extends JpaRepository<RepairLocation, Long> {

    List<RepairLocation> findAllByRepairChapterIdAndIsActiveTrue(Long repairChapterId);

    @Query("""
    SELECT rl
    FROM RepairLocation rl
    JOIN FETCH rl.repairChapter rc
    JOIN FETCH rc.airplaneType at
    ORDER BY at.name ASC, rc.id ASC, rl.id ASC
    """)
    List<RepairLocation> findAllWithAirplaneType();
}
