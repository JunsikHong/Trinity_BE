package com.engineer.Trinity_BE.domain.maintenance.repository;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceReportRepository extends JpaRepository<MaintenanceReport, Long> {
}
