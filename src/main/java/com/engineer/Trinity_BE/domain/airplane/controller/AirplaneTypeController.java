package com.engineer.Trinity_BE.domain.airplane.controller;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneTypeRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneTypeResponse;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneTypeService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airplane-type")
public class AirplaneTypeController {

    private final AirplaneTypeService airplaneTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirplaneTypeResponse>> create(@RequestBody AirplaneTypeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("CREATE_SUCCESS", airplaneTypeService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirplaneTypeResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("FIND_ALL_SUCCESS", airplaneTypeService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirplaneTypeResponse>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("FIND_ONE_SUCCESS", airplaneTypeService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirplaneTypeResponse>> update(@PathVariable Long id, @RequestBody AirplaneTypeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("UPDATE_SUCCESS", airplaneTypeService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        airplaneTypeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("DELETE_SUCCESS", null));
    }
}
