package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairFieldRepository extends JpaRepository<RepairField, Long> {
    List<RepairField> findAllByChapterIdAndIsActiveTrueOrderByFieldOrder(@Param("airplaneTypeId") Long airplaneTypeId);
}
