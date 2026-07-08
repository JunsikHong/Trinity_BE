package com.engineer.Trinity_BE.domain.repair.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RepairUpdateRequest (
        String description,
        @NotNull Long repairChapterId,
        @NotBlank String chapterType,
        List<Long> locationIds,
        Double stationStart,
        Double stationEnd,
        Double stringerStart,
        Double stringerEnd
){
}
