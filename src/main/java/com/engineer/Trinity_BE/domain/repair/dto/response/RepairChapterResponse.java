package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepairChapterResponse {

    private Long id;
    private Integer chapterNumber;
    private String chapterName;
}
