package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;

import java.time.LocalDateTime;

public record RepairResponse(
        Long id,
        Long airplaneId,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static RepairResponse from (Repair repair) {
        return new RepairResponse(
                repair.getId(),
                repair.getAirplane().getId(),
                repair.getDescription(),
                repair.getCreatedAt(),
                repair.getUpdatedAt()
        );
    }
}
