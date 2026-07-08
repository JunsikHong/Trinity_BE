package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import lombok.Builder;

@Builder
public record RepairLocationResponse(
        Long id,
        String name
) {
}
