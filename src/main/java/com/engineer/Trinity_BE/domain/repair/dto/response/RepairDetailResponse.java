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
    private String airplaneName;
    private String writerName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<DynamicValueResponse> dynamicValueResponses;

    public static RepairDetailResponse of(Repair repair, List<RepairValue> values) {
        List<DynamicValueResponse> dynamicValueResponses = values != null ?
                values.stream().map(DynamicValueResponse::from)
                        .collect(Collectors.toList())
                : List.of();

        return RepairDetailResponse.builder()
                .repairId(repair.getId())
                .airplaneId(repair.getAirplane().getId())
                .writerName(repair.getUser().getName())
                .description(repair.getDescription())
                .createdAt(repair.getCreatedAt())
                .updatedAt(repair.getUpdatedAt())
                .dynamicValueResponses(dynamicValueResponses)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicValueResponse {
        private Long fieldId;
        private String value;

        public static DynamicValueResponse from(RepairValue repairValue) {
            return DynamicValueResponse.builder()
                    .fieldId(repairValue.getRepairField().getId())
                    .value(repairValue.getValue())
                    .build();
        }
    }
}
