package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
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

    public List<RepairChapter> findAllByAirplaneTypeId(Long airplaneTypeId) {
        return repairChapterRepository.findAllByAirplaneTypeIdAndIsActive(airplaneTypeId);
    }
}
