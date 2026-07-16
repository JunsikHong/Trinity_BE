package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;

@Builder
public record RepairLocationResponse(
        Long id,
        Long repairChapterId,
        String name,
        String code,
        Integer section,
        String inputType,
        Integer sortOrder
) {
}
