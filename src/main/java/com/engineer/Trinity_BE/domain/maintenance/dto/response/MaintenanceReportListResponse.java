package com.engineer.Trinity_BE.domain.maintenance.dto.response;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MaintenanceReportListResponse {

    private Long id;

    private String writerName;

    private Double chapter;
    private Double station;
    private Double waterLine;
    private Double buttockLine;
    private Double stringer;
    private Double frame;
    private Double rib;
    private Double wingStation;
    private Double bodyStation;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MaintenanceReportListResponse from(MaintenanceReport report) {
        return MaintenanceReportListResponse.builder()
                .id(report.getId())
                .writerName(report.getUser().getName())
                .chapter(report.getChapter())
                .station(report.getStation())
                .waterLine(report.getWaterLine())
                .buttockLine(report.getButtockLine())
                .stringer(report.getStringer())
                .frame(report.getFrame())
                .rib(report.getRib())
                .wingStation(report.getWingStation())
                .bodyStation(report.getBodyStation())
                .description(report.getDescription())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }
}
