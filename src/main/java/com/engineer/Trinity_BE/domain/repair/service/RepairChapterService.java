package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterResponse;
import com.engineer.Trinity_BE.domain.repair.repository.RepairChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairChapterService {

    private final RepairChapterRepository repairChapterRepository;
    private final AirplaneTypeRepository airplaneTypeRepository;

    public List<RepairChapterResponse> findAll(Long airplaneTypeId) {


    }
}
