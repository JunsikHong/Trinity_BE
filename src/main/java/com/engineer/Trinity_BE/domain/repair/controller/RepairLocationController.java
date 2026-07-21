package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairLocationRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairLocationResponse;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.service.RepairChapterService;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class RepairLocationController {

    private final RepairLocationService repairLocationService;
    private final RepairChapterService repairChapterService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RepairLocationResponse>>> findAll() {
        List<RepairLocation> repairLocations = repairLocationService.findAll();
        List<RepairLocationResponse> responses = repairLocations.stream()
                .map(repairLocation -> {
                    RepairChapter repairChapter = repairLocation.getRepairChapter();
                    AirplaneType airplaneType = repairChapter.getAirplaneType();

                    return RepairLocationResponse.builder()
                            .id(repairLocation.getId())
                            .repairChapterId(repairChapter.getId())
                            .repairChapterNumber(repairChapter.getChapterNumber())
                            .repairChapterName(repairChapter.getChapterName())
                            .airplaneTypeId(airplaneType.getId())
                            .airplaneTypeName(airplaneType.getName())
                            .name(repairLocation.getName())
                            .code(repairLocation.getCode())
                            .section(repairLocation.getSection())
                            .inputType(repairLocation.getInputType())
                            .sortOrder(repairLocation.getSortOrder())
                            .isActive(repairLocation.isActive())
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

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(
            @RequestBody RepairLocationRequest request
    ) {
        RepairChapter repairChapter = repairChapterService.findById(request.repairChapterId());
        repairLocationService.create(request, repairChapter);
        return ResponseEntity.ok(ApiResponse.success("LOCATION_CREATE", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long id,
            @RequestBody RepairLocationRequest request
    ) {
        RepairChapter repairChapter = repairChapterService.findById(request.repairChapterId());
        repairLocationService.update(id, request, repairChapter);
        return ResponseEntity.ok(ApiResponse.success("LOCATION_UPDATE", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {
        repairLocationService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("LOCATION_DELETE", null));
    }
}
