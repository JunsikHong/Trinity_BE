package com.engineer.Trinity_BE.domain.maintenance.dto.response;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MaintenanceReportDetailResponse {

    private Long id;

    private Long writerId;
    private String writerName;

    private Long airplaneId;

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

    private List<MaintenanceReportFileResponse> files;

    public static MaintenanceReportDetailResponse of(MaintenanceReport report, List<MaintenanceReportFileResponse> files) {
        return MaintenanceReportDetailResponse.builder()
                .id(report.getId())
                .writerId(report.getUser().getId())
                .writerName(report.getUser().getName())
                .airplaneId(report.getAirplane().getId())
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
                .files(files)
                .build();
    }
}
