package com.engineer.Trinity_BE.domain.maintenance.repository;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReportFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceReportFileRepository extends JpaRepository<MaintenanceReportFile, Long> {
}
