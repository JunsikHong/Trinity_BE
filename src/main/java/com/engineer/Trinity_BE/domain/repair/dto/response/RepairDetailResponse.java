package com.engineer.Trinity_BE.domain.repair.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record RepairDetailResponse(
        Long id,
        Long airplaneId,
        String description,
        String chapterType,
        List<RepairLocationResponse> locations,
        RepairFuselageResponse fuselage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
