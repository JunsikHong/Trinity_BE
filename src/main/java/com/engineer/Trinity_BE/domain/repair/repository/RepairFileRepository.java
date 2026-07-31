package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepairFileRepository extends JpaRepository<RepairFile, Long> {

    List<RepairFile> findByRepairId(Long repairId);
    List<RepairFile> findByRepairIdInOrderByIdAsc(List<Long> repairIds);
}
