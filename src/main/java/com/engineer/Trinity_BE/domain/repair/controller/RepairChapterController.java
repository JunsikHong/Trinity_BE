package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterResponse;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.service.RepairChapterService;
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
@RequestMapping("/api/v1/chapter")
public class RepairChapterController {

    private final RepairChapterService repairChapterService;
    private final AirplaneService airplaneService;

    @GetMapping("/{airplaneId}")
    public ResponseEntity<ApiResponse<List<RepairChapterResponse>>> findAll(
        @PathVariable Long airplaneId
    ) {
        Airplane airplane = airplaneService.findOne(airplaneId);
        List<RepairChapter> repairChapters = repairChapterService.findAllByAirplaneTypeId(airplane.getAirplaneType().getId());
        List<RepairChapterResponse> responses = repairChapters.stream()
                .map(repairChapter -> {
                    return RepairChapterResponse.builder()
                            .id(repairChapter.getId())
                            .chapterNumber(repairChapter.getChapterNumber())
                            .chapterName(repairChapter.getChapterName())
                            .chapterDescription(repairChapter.getChapterDescription())
                            .build();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("CHAPTER_LIST", responses));
    }
}
