package com.engineer.Trinity_BE.domain.airplane.dto.response;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import lombok.Getter;

@Getter
public class AirplaneResponse {

    private final Long id;
    private final Long airplaneTypeId;
    private final String airplaneTypeName;
    private final String registrationNumber;

    public AirplaneResponse(Airplane airplane) {
        this.id = airplane.getId();
        this.airplaneTypeId = airplane.getAirplaneType().getId();
        this.airplaneTypeName = airplane.getAirplaneType().getName();
        this.registrationNumber = airplane.getRegistrationNumber();
    }
}
