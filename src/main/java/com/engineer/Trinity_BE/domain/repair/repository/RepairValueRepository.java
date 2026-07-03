package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairValueRepository extends JpaRepository<RepairValue, Long> {

    @Query("select rv from RepairValue rv join fetch rv.repairField join fetch rv.repairField.chapter where rv.repair.id = :repairId")
    List<RepairValue> findAllByRepairId(Long repairId);

    @Query("select rv from RepairValue rv join fetch rv.repairField join fetch rv.repairField.chapter where rv.repair.id in :repairIds")
    List<RepairValue> findAllByRepairIds(List<Long> repairIds);

    void deleteAllByRepairId(Long repairId);
}
