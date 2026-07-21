package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;

@Builder
public record RepairLocationResponse(
        Long id,
        Long repairChapterId,
        Integer repairChapterNumber,
        String repairChapterName,
        Long airplaneTypeId,
        String airplaneTypeName,
        String name,
        String code,
        Integer section,
        String inputType,
        Integer sortOrder,
        boolean isActive
) {
}
