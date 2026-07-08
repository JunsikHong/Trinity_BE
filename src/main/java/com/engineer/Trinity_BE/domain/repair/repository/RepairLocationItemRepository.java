package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairLocationItemRepository extends JpaRepository<RepairLocationItem, Long> {
    List<RepairLocationItem> findByRepairId(Long repairId);

    void deleteByRepairId(Long repairId);
}
