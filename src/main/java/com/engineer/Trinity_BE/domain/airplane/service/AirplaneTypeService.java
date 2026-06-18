package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.dto.request.AirplaneTypeRequest;
import com.engineer.Trinity_BE.domain.airplane.dto.response.AirplaneTypeResponse;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneTypeService {

    private final AirplaneTypeRepository airplaneTypeRepository;

    public AirplaneTypeResponse create (AirplaneTypeRequest request) {
        AirplaneType type = AirplaneType.builder()
                .name(request.getName())
                .build();

        return new AirplaneTypeResponse(airplaneTypeRepository.save(type));
    }

    @Transactional
    public List<AirplaneTypeResponse> findAll() {
        return airplaneTypeRepository.findAll()
                .stream()
                .map(AirplaneTypeResponse::new)
                .toList();
    }

    @Transactional
    public AirplaneTypeResponse findById(Long id) {
        AirplaneType airplaneType = airplaneTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일치하는 항공기 타입이 없습니다."));
        return new AirplaneTypeResponse(airplaneType);
    }

    @Transactional
    public AirplaneTypeResponse update(Long id, AirplaneTypeRequest request) {
        AirplaneType airplaneType = airplaneTypeRepository.findById(id)
                .orElseThrow();

        airplaneType.changeName(request.getName());

        return new AirplaneTypeResponse(airplaneType);
    }

    public void delete(Long id) {
        airplaneTypeRepository.deleteById(id);
    }
}
