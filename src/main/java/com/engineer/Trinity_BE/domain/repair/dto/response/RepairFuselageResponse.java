package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFuselage;

public record RepairFuselageResponse(
        Double stationStart,
        Double stationEnd,
        Double stringerStart,
        Double stringerEnd
) {
    public static RepairFuselageResponse from(RepairFuselage repairFuselage) {
        return new RepairFuselageResponse(
                repairFuselage.getStationStart(),
                repairFuselage.getStationEnd(),
                repairFuselage.getStringerStart(),
                repairFuselage.getStringerEnd()
        );
    }
}
