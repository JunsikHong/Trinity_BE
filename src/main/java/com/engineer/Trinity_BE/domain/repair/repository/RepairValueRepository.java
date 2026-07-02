package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairValueRepository extends JpaRepository<RepairValue, Long> {

    @Query("select v from RepairValue v join fetch v.repairField where v.repair.id = :repairId")
    List<RepairValue> findAllByRepairIdWithField(@Param("repairId") Long repairId);

    @Query("select rv from RepairValue rv join fetch rv.repairField where rv.repair.id in :repairIds")
    List<RepairValue> findAllByRepairIdsWithField(@Param("repairIds") List<Long> repairIds);

    void deleteAllByRepairId(Long repairId);
}
