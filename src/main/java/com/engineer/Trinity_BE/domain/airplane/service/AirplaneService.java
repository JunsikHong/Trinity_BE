package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    public Airplane findOne(Long airplaneId) {
        return airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 비행기입니다."));
    }
}
