package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepairLocationRepository extends JpaRepository<RepairLocation, Long> {

    List<RepairLocation> findAllByRepairChapterIdAndIsActiveTrue(Long repairChapterId);

    boolean existsByName(String name);
}
