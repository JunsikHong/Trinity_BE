package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneTypeService;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairStatRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairStatResponse;
import com.engineer.Trinity_BE.domain.repair.enums.StatPeriod;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationItemService;
import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import com.engineer.Trinity_BE.domain.repair.service.RepairStatService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair-stat")
public class RepairStatController {

    // 기간검색(전체, 1개월, 3개월, 6개월, 1년)
    // 전체 수리이력
    // 기종별 수리이력 (매개변수 : 기종, 기간)
    // 비행기별 수리이력 (매개변수 : 비행기 등록번호, 기간)
    // 챕터별 수리이력 (매개변수 : 비행기 등록번호, 챕터, 기간)
    // 부위별 수리이력 (매개변수 : 비행기 등록번호, 챕터, 부위, 기간)

    private final RepairStatService repairStatService;

    @GetMapping
    public ResponseEntity<ApiResponse<RepairStatResponse>> getStatistics(
            @ModelAttribute RepairStatRequest request
    ) {
        RepairStatResponse response = repairStatService.getStatistics(request);
        return ResponseEntity.ok(ApiResponse.success("STAT_RESULT", response));
    }

}
