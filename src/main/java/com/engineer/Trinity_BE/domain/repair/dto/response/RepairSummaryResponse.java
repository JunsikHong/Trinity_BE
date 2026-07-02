package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RepairSummaryResponse {

    private Long repairId;
    private Long airplaneId;
    private String airplaneRegistrationNumber;
    private String writerName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<RepairValueResponse> repairValueResponses;
    private RepairFileResponse repairFileResponse;
}
