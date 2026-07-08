package com.engineer.Trinity_BE.domain.repair.dto.response;

import java.util.List;

public record RepairChapterInputResponse(
        Long repairChapterId,
        String chapterType,
        List<RepairLocationResponse> locations
) {
}
