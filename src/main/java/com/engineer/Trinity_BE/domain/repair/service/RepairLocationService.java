package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairLocationService {

    private final RepairLocationRepository repairLocationRepository;

    public List<RepairLocation> findAllByRepairChapterId(Long repairChapterId) {
        return repairLocationRepository.findAllByRepairChapterIdAndIsActive(repairChapterId);
    }
}
