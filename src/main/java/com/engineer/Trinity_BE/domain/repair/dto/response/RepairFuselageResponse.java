package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepairFuselageResponse {

    private Long repairFuselageId;
    private Double stationStart;
    private Double stationEnd;
    private Double stringerStart;
    private Double stringerEnd;
}
