package com.engineer.Trinity_BE.domain.repair.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequest {

    private Long airplaneId;
    private String description;
    private List<DynamicFieldValue> dynamicFieldValues;

    @Getter
    public static class DynamicFieldValue {
        private Long fieldId;
        private String value;
    }
}
