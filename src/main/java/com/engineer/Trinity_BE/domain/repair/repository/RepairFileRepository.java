package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairFileRepository extends JpaRepository<RepairFile, Long> {
}
