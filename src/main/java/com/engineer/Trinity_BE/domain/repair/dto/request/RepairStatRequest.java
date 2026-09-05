package com.engineer.Trinity_BE.domain.repair.dto.request;

import com.engineer.Trinity_BE.domain.repair.enums.StatPeriod;
import com.engineer.Trinity_BE.domain.repair.enums.StatType;

public record RepairStatRequest(
    StatType type,
    StatPeriod period,
    Long airplaneTypeId,
    Long airplaneId,
    String chapter
) {
}
