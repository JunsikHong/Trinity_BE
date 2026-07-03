package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class RepairDetailResponse {

    private Long repairId;
    private Long airplaneId;
    private String airplaneRegistrationNumber;
    private String writerName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<RepairValueResponse> repairValueResponses;
    private List<RepairFuselageResponse> repairFuselageResponses;
    private List<RepairFileResponse> repairFileResponses;
}
