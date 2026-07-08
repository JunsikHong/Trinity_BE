package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;

public record RepairChapterResponse (
        Long id,
        Integer chapterNumber,
        String chapterName,
        String chapterType
) {
    public static RepairChapterResponse from (RepairChapter repairChapter) {
        return new RepairChapterResponse(
                repairChapter.getId(),
                repairChapter.getChapterNumber(),
                repairChapter.getChapterName(),
                repairChapter.getChapterType()
        );
    }
}
