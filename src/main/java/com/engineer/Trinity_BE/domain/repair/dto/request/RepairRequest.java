package com.engineer.Trinity_BE.domain.repair.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest {

    private Long repairId;
    private Long airplaneId;
    private String description;
    private List<RepairValueRequest> values;
    private List<Long> deleteFileIds;

    @Getter
    @Setter
    public static class RepairValueRequest {
        private Long repairFieldId;
        private String value;
    }
}
