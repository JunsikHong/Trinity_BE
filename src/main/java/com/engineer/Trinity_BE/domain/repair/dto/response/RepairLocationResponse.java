package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;

public record RepairLocationResponse(
        Long id,
        String name
) {
    public static RepairLocationResponse from (RepairLocation repairLocation) {
        return new RepairLocationResponse(repairLocation.getId(), repairLocation.getName());
    }
}
