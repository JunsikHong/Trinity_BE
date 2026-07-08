package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneTypeRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneTypeResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneTypeService {

    private final AirplaneTypeRepository airplaneTypeRepository;

    public AirplaneType getAirplaneType(Long airplaneTypeId) {
        return airplaneTypeRepository.findById(airplaneTypeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 기종입니다."));
    }
}
