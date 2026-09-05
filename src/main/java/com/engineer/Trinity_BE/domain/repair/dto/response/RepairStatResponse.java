package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairStatRequest;
import com.engineer.Trinity_BE.domain.repair.enums.StatPeriod;
import com.engineer.Trinity_BE.domain.repair.enums.StatType;
import com.engineer.Trinity_BE.domain.repair.enums.StatUnit;

import java.util.List;

public record RepairStatResponse(
        StatType target,
        StatPeriod period,
        StatUnit unit,
        List<RepairStatItem> data
) {
    public static RepairStatResponse of(
            RepairStatRequest request,
            StatUnit unit,
            List<RepairStatItem> data
    ) {
        return new RepairStatResponse(
                request.type(),
                request.period(),
                unit,
                data
        );
    }
}
