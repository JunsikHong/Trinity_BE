package com.engineer.Trinity_BE.domain.airplane.service;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneTypeService {

    private final AirplaneTypeRepository airplaneTypeRepository;

    public List<AirplaneType> findAll() {
        return airplaneTypeRepository.findAll();
    }
}
