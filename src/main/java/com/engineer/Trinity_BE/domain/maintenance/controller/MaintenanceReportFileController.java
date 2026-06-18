package com.engineer.Trinity_BE.domain.maintenance.controller;

import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportFileResponse;
import com.engineer.Trinity_BE.domain.maintenance.service.MaintenanceReportFileService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance-report/{reportId}/files")
@RequiredArgsConstructor
public class MaintenanceReportFileController {

    private final MaintenanceReportFileService maintenanceReportFileService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaintenanceReportFileResponse>>> findAll(@PathVariable Long reportId) {
        List<MaintenanceReportFileResponse> response = maintenanceReportFileService.findAllByReportId(reportId)
                .stream()
                .map(MaintenanceReportFileResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("파일 목록 조회에 성공했습니다.", response));
    }

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long reportId,
            @PathVariable Long fileId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        maintenanceReportFileService.deleteFile(fileId, userDetails);
        return ResponseEntity.ok(ApiResponse.success("파일이 삭제되었습니다.", null));
    }
}
