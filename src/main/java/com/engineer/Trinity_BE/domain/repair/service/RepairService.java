package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;

    @Transactional
    public Repair createRepair(Airplane airplane, String description) {

        //유저정보 추가
        Repair repair = Repair.builder()
                .airplane(airplane)
                .description(description)
                .build();
        return repairRepository.save(repair);
    }

    @Transactional
    public Repair updateDescription (Long repairId, String description) {
        Repair repair = getRepair(repairId);
        repair.changeDescription(description);
        return repair;
    }

    @Transactional
    public void deleteRepair(Long repairId) {
        Repair repair = getRepair(repairId);
        repairRepository.delete(repair);
    }

    public Repair getRepair(Long repairId) {
        return repairRepository.findById(repairId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 정비이력입니다."));
    }
}
