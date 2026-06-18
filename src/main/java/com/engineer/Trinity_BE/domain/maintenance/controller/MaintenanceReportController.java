package com.engineer.Trinity_BE.domain.maintenance.controller;

import com.engineer.Trinity_BE.domain.maintenance.dto.request.MaintenanceReportRequest;
import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportDetailResponse;
import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportListResponse;
import com.engineer.Trinity_BE.domain.maintenance.service.MaintenanceReportService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maintenance-report")
public class MaintenanceReportController {

    private final MaintenanceReportService maintenanceReportService;

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<MaintenanceReportDetailResponse>> create(
            @Valid @RequestPart("request")MaintenanceReportRequest request,
            @RequestPart(value = "files", required = false)List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MaintenanceReportDetailResponse response = maintenanceReportService.create(request, files, userDetails);
        return ResponseEntity.ok(ApiResponse.success("정비 이력이 등록되었습니다.", response));
    }

    @PreAuthorize("hasAnyRole('EDITOR', ADMIN)")
    @PutMapping(value = "/{reportId}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<MaintenanceReportDetailResponse>> update(
            @PathVariable Long reportId,
            @Valid @RequestPart("request") MaintenanceReportRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MaintenanceReportDetailResponse response = maintenanceReportService.update(reportId, request, files, userDetails);
        return ResponseEntity.ok(ApiResponse.success("정비 이력이 수정되었습니다.", response));
    }

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @DeleteMapping("/{reportId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long reportId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        maintenanceReportService.delete(reportId, userDetails);
        return ResponseEntity.ok(ApiResponse.success("정비 이력이 삭제되었습니다.", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaintenanceReportListResponse>>> findAll() {
        List<MaintenanceReportListResponse> response = maintenanceReportService.findAll();
        return ResponseEntity.ok(ApiResponse.success("정비 이력 목록 조회에 성공했습니다.", response));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<MaintenanceReportDetailResponse>> findOne(@PathVariable Long reportId) {
        MaintenanceReportDetailResponse response = maintenanceReportService.getDetail(reportId);
        return ResponseEntity.ok(ApiResponse.success("정비 이력 상세 조회에 성공했습니다.", response));
    }
}
