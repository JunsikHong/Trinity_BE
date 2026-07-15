package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairLocationRepository extends JpaRepository<RepairLocation, Long> {

    List<RepairLocation> findAllByRepairChapterIdAndIsActiveTrue(Long repairChapterId);
}
