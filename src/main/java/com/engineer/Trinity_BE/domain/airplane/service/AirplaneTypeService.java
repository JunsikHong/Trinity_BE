package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneTypeRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneTypeResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirplaneTypeService {

    private final AirplaneTypeRepository airplaneTypeRepository;

    public AirplaneType create (AirplaneTypeRequest request) {
        AirplaneType type = AirplaneType.builder()
                .name(request.getName())
                .build();

        return new AirplaneTypeResponse(airplaneTypeRepository.save(type));
    }
}
