package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairFileRepository extends JpaRepository<RepairFile, Long> {

    List<RepairFile> findAllByRepairId(Long repairId);
    void deleteAllByRepairId(Long repairId);
}
