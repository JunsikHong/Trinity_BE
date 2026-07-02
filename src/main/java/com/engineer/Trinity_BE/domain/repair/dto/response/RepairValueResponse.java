package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepairValueResponse {

    private Long repairFieldId;
    private String fieldLabel;
    private String fieldName;
    private String fieldType;
    private boolean required;
    private Integer fieldOrder;
    private String value;
}
