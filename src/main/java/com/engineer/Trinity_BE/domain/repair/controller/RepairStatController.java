package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneTypeService;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationItemService;
import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair-stat")
public class RepairStatController {

    private final RepairService repairService;
    private final AirplaneService airplaneService;
    private final AirplaneTypeService airplaneTypeService;
    private final RepairLocationItemService repairLocationItemService;

    // 기간검색, 전체 수리이력
    // 기간검색, 기종별 수리이력
    // 기간검색, 비행기별 수리이력
    // 최근 전체 수리이력
    // 내가 쓴 수리이력
    
    // 차트 검색
    // 기간검색, 전체 수리이력 비율
    // 기간검색, 기종별 수리이력 비율
    // 기간검색, 비행기별 수리이력 비율
}
