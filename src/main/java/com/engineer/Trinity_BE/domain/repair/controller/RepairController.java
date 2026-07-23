package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortBy;
import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortDirection;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairRequest;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairSearchRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.CursorPageResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairResponse;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.service.RepairChapterService;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationItemService;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationService;
import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.service.UserService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair")
public class RepairController {

    private final RepairService repairService;
    private final AirplaneService airplaneService;
    private final UserService userService;
    private final RepairLocationItemService repairLocationItemService;

    @GetMapping("/{airplaneId}")
    public ResponseEntity<ApiResponse<CursorPageResponse<RepairResponse>>> findAll(
            @PathVariable Long airplaneId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long chapterId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "REPAIR_AT") RepairSortBy sortBy,
            @RequestParam(defaultValue = "DESC") RepairSortDirection sortDirection,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorValue,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") int size
    ) {
        RepairSearchRequest request = new RepairSearchRequest(search, chapterId, startDate, endDate, sortBy, sortDirection);
        CursorPageResponse<RepairResponse> responses = repairService.findAllByAirplaneId(airplaneId, request, cursorValue, cursorId, size);
        return ResponseEntity.ok(ApiResponse.success("REPAIR_LIST", responses));
    }

    @GetMapping("/detail/{repairId}")
    public ResponseEntity<ApiResponse<RepairResponse>> findOne(
            @PathVariable Long repairId
    ) {
        RepairResponse response = RepairResponse.from(repairService.findOne(repairId));
        return ResponseEntity.ok(ApiResponse.success("REPAIR_DETAIL", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RepairRequest request
    ) {
        Airplane airplane = airplaneService.findOne(request.airplaneId());
        User user = userService.findOne(customUserDetails.getUserId());
        Repair repair = repairService.create(user, airplane, request);
        repairLocationItemService.create(repair, request);
        return ResponseEntity.ok(ApiResponse.success("REPAIR_CREATE", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id,
            @RequestBody RepairRequest request
    ) throws Exception{
        User user = userService.findOne(customUserDetails.getUserId());
        Repair repair = repairService.update(user, id, request);
        repairLocationItemService.update(repair, request);
        return ResponseEntity.ok(ApiResponse.success("LOCATION_UPDATE", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    ) {
        User user = userService.findOne(customUserDetails.getUserId());
        repairService.delete(user, id);
        return ResponseEntity.ok(ApiResponse.success("REPAIR_DELETE", null));
    }
}
