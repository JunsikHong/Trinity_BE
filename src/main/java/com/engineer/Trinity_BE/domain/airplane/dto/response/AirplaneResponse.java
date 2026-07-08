package com.engineer.Trinity_BE.domain.airplane.dto.response;

import lombok.Builder;

@Builder
public record AirplaneResponse(
        Long id,
        Long airplaneTypeId,
        String airplaneTypeName,
        String registrationNumber
) {
}
