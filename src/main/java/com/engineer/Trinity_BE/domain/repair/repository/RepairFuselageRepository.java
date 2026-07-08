package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFuselage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepairFuselageRepository extends JpaRepository<RepairFuselage, Long> {

    Optional<RepairFuselage> findByRepairId(Long repairId);
    void deleteByRepairId(Long repairId);
}
