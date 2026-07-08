package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.entity.RepairFuselage;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFuselageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairFuselageService {

    private final RepairFuselageRepository repairFuselageRepository;

    @Transactional
    public RepairFuselage save(Repair repair, RepairChapter repairChapter, Double stationStart, Double stationEnd, Double stringerStart, Double stringerEnd) {
        RepairFuselage repairFuselage = RepairFuselage.builder()
                .repair(repair)
                .repairChapter(repairChapter)
                .stationStart(stationStart)
                .stationEnd(stationEnd)
                .stringerStart(stringerStart)
                .stringerEnd(stringerEnd)
                .build();
        return repairFuselageRepository.save(repairFuselage);
    }

    public RepairFuselage getByRepair(Long repairId) {
        return repairFuselageRepository.findByRepairId(repairId).orElse(null);
    }

    @Transactional
    public void deleteByRepair(Long repairId) {
        repairFuselageRepository.deleteByRepairId(repairId);
    }
}
