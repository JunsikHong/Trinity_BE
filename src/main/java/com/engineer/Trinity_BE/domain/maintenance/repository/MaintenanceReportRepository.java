package com.engineer.Trinity_BE.domain.maintenance.repository;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MaintenanceReportRepository extends JpaRepository<MaintenanceReport, Long> {

    @Query("select r from MaintenanceReport r join fetch r.user order by r.id desc")
    List<MaintenanceReport> findAllWithUser();

    @Query("select r from MaintenanceReport r join fetch r.user join fetch r.airplane where r.id = :id")
    Optional<MaintenanceReport> findByIdWithUserAndAirplane(@Param("id") Long id);
}
