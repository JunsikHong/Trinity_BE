package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFieldRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairValueRepository;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final RepairFieldRepository repairFieldRepository;
    private final RepairValueRepository repairValueRepository;
    private final UserRepository userRepository;
    private final AirplaneRepository airplaneRepository;


}
