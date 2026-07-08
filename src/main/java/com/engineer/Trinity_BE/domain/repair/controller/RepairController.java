package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairCreateRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterInputResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairResponse;
import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import com.engineer.Trinity_BE.repair.RepairDetail;
import com.engineer.Trinity_BE.repair.RepairRead;
import com.engineer.Trinity_BE.repair.RepairWrite;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair")
public class RepairController {

    private final RepairRead repairRead;
    private final RepairWrite repairWrite;
    private final RepairDetail repairDetail;

    @GetMapping("/chapters")
    public ResponseEntity<List<RepairChapterResponse>> getChapters(
            @RequestParam Long airplaneTypeId
    ) {
        return ResponseEntity.ok(repairRead.getChapters(airplaneTypeId));
    }

    @GetMapping("/chapters/{repairChapterId}/input")
    public ResponseEntity<RepairChapterInputResponse> getChapterInput(
            @PathVariable Long repairChapterId
    ) {
        return ResponseEntity.ok(repairRead.getChapterInput(repairChapterId));
    }

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<RepairResponse>> create (
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody RepairCreateRequest request
    ) {
        RepairResponse response = repairWrite.createRepair(userDetails.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.success("정비이력이 등록되었습니다.", null));
    }

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @PutMapping(value = "/{reportId}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> update (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.success("정비이력이 수정되었습니다.", null));
    }

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @DeleteMapping("/{reportId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.success("정비이력이 삭제되었습니다.", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> findAll (
            @RequestParam(required = false) Long airplaneId
    ) {
        return ResponseEntity.ok(ApiResponse.success("정비이력 목록 조회에 성공하였습니다.", null));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<Void>> findOne(
            @PathVariable Long reportId
    ) {
        return ResponseEntity.ok(ApiResponse.success("정비이력 상세 조회에 성공하였습니다.", null));
    }
}
