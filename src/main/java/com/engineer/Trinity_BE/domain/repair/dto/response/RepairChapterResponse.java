package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;

@Builder
public record RepairChapterResponse (
        Long id,
        Integer chapterNumber,
        String chapterName,
        String chapterDescription
) {
}
