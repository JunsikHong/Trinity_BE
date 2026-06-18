package com.engineer.Trinity_BE.domain.maintenance.repository;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReportFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceReportFileRepository extends JpaRepository<MaintenanceReportFile, Long> {
    List<MaintenanceReportFile> findAllByMaintenanceReportId(Long reportId);
    void deleteAllByMaintenanceReportId(Long maintenanceReportId);
}
