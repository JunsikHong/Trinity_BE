package com.engineer.Trinity_BE.domain.airplane.controller;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneResponse;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirplaneResponse>> create(@RequestBody AirplaneRequest request) {
        return ResponseEntity.ok(ApiResponse.success("CREATE_SUCCESS", airplaneService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirplaneResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("FIND_ALL_SUCCESS", airplaneService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirplaneResponse>> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("FIND_ONE_SUCCESS", airplaneService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AirplaneResponse>> update(@PathVariable Long id, @RequestBody AirplaneRequest request) {
        return ResponseEntity.ok(ApiResponse.success("UPDATE_SUCCESS", airplaneService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        airplaneService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("DELETE_SUCCESS", null));
    }
}
