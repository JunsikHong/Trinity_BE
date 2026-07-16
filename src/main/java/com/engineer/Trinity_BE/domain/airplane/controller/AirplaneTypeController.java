package com.engineer.Trinity_BE.domain.airplane.controller;

import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneTypeResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneTypeService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airplane-type")
public class AirplaneTypeController {

    private final AirplaneTypeService airplaneTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirplaneTypeResponse>>> findAll() {
        List<AirplaneType> airplaneTypes = airplaneTypeService.findAll();
        List<AirplaneTypeResponse> responses = airplaneTypes.stream()
                .map(airplaneType -> {
                    return AirplaneTypeResponse.builder()
                            .id(airplaneType.getId())
                            .name(airplaneType.getName())
                            .build();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("AIRPLANE_TYPE_LIST", responses));
    }
}
