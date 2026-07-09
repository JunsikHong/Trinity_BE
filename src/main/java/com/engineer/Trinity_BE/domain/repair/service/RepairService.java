package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;

    public Repair findOne(Long repairId) {
        return repairRepository.findDetail(repairId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 수리이력입니다."));
    }

    @Transactional
    public List<Repair> findAllByAirplaneId(Long airplaneId) {
        return repairRepository.findAllWithLocations(airplaneId);
    }

    @Transactional
    public Repair create(User user, Airplane airplane, String description) {
        Repair repair = Repair.builder()
                .user(user)
                .airplane(airplane)
                .description(description)
                .build();
        return repairRepository.save(repair);
    }

    @Transactional
    public Repair update(Long repairId, String description, LocalDateTime repairAt) {
        Repair repair = repairRepository.findDetail(repairId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 수리이력입니다."));
        repair.update(description, repairAt);
        return repair;
    }
}
