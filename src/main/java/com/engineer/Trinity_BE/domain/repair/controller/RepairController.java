package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair")
public class RepairController {

    private final RepairService repairService;

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> create (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
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
