package com.engineer.Trinity_BE.domain.airplane.controller;

import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneTypeService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneTypeService airplaneTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirplaneResponse>>> findAll() {
        List<Airplane> airplanes = airplaneService.findAll();
        List<AirplaneType> airplaneTypes = airplaneTypeService.findAll();

        Map<Long, AirplaneType> typeMap = airplaneTypes.stream()
                .collect(Collectors.toMap(AirplaneType::getId, Function.identity()));

        List<AirplaneResponse> responses = airplanes.stream()
                .map(airplane -> {
                    AirplaneType type = typeMap.get(airplane.getAirplaneType().getId());
                    return AirplaneResponse.builder()
                            .id(airplane.getId())
                            .registrationNumber(airplane.getRegistrationNumber())
                            .airplaneTypeId(type.getId())
                            .airplaneTypeName(type.getName())
                            .build();
                }).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("AIRPLANE_LIST", responses));
    }

}
