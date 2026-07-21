package com.engineer.Trinity_BE.domain.repair.dto.request;

import jakarta.validation.constraints.NotNull;

public record RepairLocationRequest(
        @NotNull Long repairChapterId,
        String name,
        String code,
        Integer section,
        String inputType,
        Integer sortOrder,
        boolean isActive
) {
}
