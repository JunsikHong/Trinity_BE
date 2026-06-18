package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneTypeRepository airplaneTypeRepository;

    public AirplaneResponse create(AirplaneRequest request) {
        AirplaneType airplaneType = airplaneTypeRepository.findById(request.getAirplaneTypeId())
                .orElseThrow();

        Airplane airplane = Airplane.builder()
                .airplaneType(airplaneType)
                .registrationNumber(request.getRegistrationNumber())
                .build();

        return new AirplaneResponse(airplaneRepository.save(airplane));
    }

    @Transactional
    public List<AirplaneResponse> findAll() {
        return airplaneRepository.findAll()
                .stream()
                .map(AirplaneResponse::new)
                .toList();
    }

    @Transactional
    public AirplaneResponse findById(Long id) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일치하는 항공기가 없습니다."));
        return new AirplaneResponse(airplane);
    }

    @Transactional
    public AirplaneResponse update(Long id, AirplaneRequest request) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow();

        AirplaneType airplaneType = airplaneTypeRepository.findById(request.getAirplaneTypeId())
                .orElseThrow();

        airplane.change(airplaneType, request.getRegistrationNumber());

        return new AirplaneResponse(airplane);
    }

    public void delete(Long id) {
        airplaneRepository.deleteById(id);
    }
}
