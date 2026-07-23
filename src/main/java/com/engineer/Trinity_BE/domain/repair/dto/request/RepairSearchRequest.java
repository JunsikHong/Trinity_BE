package com.engineer.Trinity_BE.domain.repair.dto.request;

import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortBy;
import com.engineer.Trinity_BE.domain.repair.dto.enums.RepairSortDirection;

import java.time.LocalDate;

public record RepairSearchRequest(
        String search,
        Long chapterId,
        LocalDate startDate,
        LocalDate endDate,
        RepairSortBy sortBy,
        RepairSortDirection sortDirection
) {
}
