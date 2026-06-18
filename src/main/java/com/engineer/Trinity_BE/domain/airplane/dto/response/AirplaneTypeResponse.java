package com.engineer.Trinity_BE.domain.airplane.dto.response;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import lombok.Getter;

@Getter
public class AirplaneTypeResponse {
    private final Long id;
    private final String name;

    public AirplaneTypeResponse(AirplaneType airplaneType) {
        this.id = airplaneType.getId();
        this.name = airplaneType.getName();
    }
}
