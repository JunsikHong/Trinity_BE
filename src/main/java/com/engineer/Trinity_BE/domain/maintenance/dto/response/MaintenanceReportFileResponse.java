package com.engineer.Trinity_BE.domain.maintenance.dto.response;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReportFile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MaintenanceReportFileResponse {

    private Long id;
    private String originalName;
    private String filePath;
    private String extension;
    private String mimeType;
    private Long size;
    private LocalDateTime createdAt;

    public static MaintenanceReportFileResponse from(MaintenanceReportFile file) {
        return MaintenanceReportFileResponse.builder()
                .id(file.getId())
                .originalName(file.getOriginalName())
                .filePath(file.getFilePath())
                .extension(file.getExtension())
                .mimeType(file.getMimeType())
                .size(file.getSize())
                .createdAt(file.getCreatedAt())
                .build();
    }
}
