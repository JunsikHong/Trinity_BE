package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairFieldService {

    private final RepairFieldRepository repairFieldRepository;
    private final AirplaneRepository airplaneRepository;

}
