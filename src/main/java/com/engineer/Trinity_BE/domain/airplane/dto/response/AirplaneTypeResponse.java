package com.engineer.Trinity_BE.domain.airplane.dto.response;

import lombok.Builder;

@Builder
public record AirplaneTypeResponse(
        Long id,
        String name
) {
}
