package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.enums.StatPeriod;
import com.engineer.Trinity_BE.domain.repair.enums.StatTarget;

public record RepairStatResponse(
        StatPeriod period,
        StatTarget target,
        Long targetId,
        String targetName,
        Long totalCount

) {
}
