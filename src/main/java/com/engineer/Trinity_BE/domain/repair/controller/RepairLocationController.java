package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.repair.dto.response.RepairLocationResponse;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class RepairLocationController {

    private final RepairLocationService repairLocationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RepairLocationResponse>>> findAll() {
        List<RepairLocation> repairLocations = repairLocationService.findAll();
        List<RepairLocationResponse> responses = repairLocations.stream()
                .map(repairLocation -> {
                    return RepairLocationResponse.builder()
                            .id(repairLocation.getId())
                            .repairChapterId(repairLocation.getRepairChapter().getId())
                            .name(repairLocation.getName())
                            .code(repairLocation.getCode())
                            .section(repairLocation.getSection())
                            .inputType(repairLocation.getInputType())
                            .sortOrder(repairLocation.getSortOrder())
                            .build();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("LOCATION_LIST", responses));
    }

    @GetMapping("/{repairChapterId}")
    public ResponseEntity<ApiResponse<List<RepairLocationResponse>>> findAllByRepairChapterId(
            @PathVariable Long repairChapterId
    ) {
        List<RepairLocation> repairLocations = repairLocationService.findAllByRepairChapterId(repairChapterId);
        List<RepairLocationResponse> responses = repairLocations.stream()
                .map(repairLocation -> {
                    return RepairLocationResponse.builder()
                            .id(repairLocation.getId())
                            .repairChapterId(repairLocation.getRepairChapter().getId())
                            .name(repairLocation.getName())
                            .code(repairLocation.getCode())
                            .section(repairLocation.getSection())
                            .inputType(repairLocation.getInputType())
                            .sortOrder(repairLocation.getSortOrder())
                            .build();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("LOCATION_LIST", responses));
    }
}
