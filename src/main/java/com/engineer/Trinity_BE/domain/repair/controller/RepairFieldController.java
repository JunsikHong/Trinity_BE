package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.repair.service.RepairFieldService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair-field")
public class RepairFieldController {

    private final RepairFieldService repairFieldService;

    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    @GetMapping("/{airplaneTypeId}")
    public ResponseEntity<ApiResponse<Void>> findAll(
            @PathVariable Long airplaneTypeId
    ) {
        return ResponseEntity.ok(ApiResponse.success("정비이력 입력필드 조회에 성공하였습니다.", null));
    }
}
