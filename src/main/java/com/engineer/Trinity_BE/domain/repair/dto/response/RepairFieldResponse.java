package com.engineer.Trinity_BE.domain.repair.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RepairFieldResponse {

    private Long chapterId;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private Long repairFieldId;
        private String fieldLabel;
        private String fieldName;
        private String fieldType;
        private boolean required;
        private Integer fieldOrder;
        private String value;
    }
}
