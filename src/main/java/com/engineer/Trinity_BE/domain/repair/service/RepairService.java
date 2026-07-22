package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairRequest;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
    public Repair create(User user, Airplane airplane, RepairRequest request) {
        Repair repair = Repair.builder()
                .user(user)
                .airplane(airplane)
                .description(request.description())
                .repairAt(request.repairAt().atStartOfDay())
                .build();
        return repairRepository.save(repair);
    }

    @Transactional
    public Repair update(User user, Long repairId, RepairRequest request) throws Exception{
        Repair repair = repairRepository.findDetail(repairId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 수리이력입니다."));

        if(!repair.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("수정권한이 없습니다.");
        }

        repair.update(request.description(), request.repairAt().atStartOfDay());
        return repair;
    }

    @Transactional
    public void delete(User user, Long id) {
        Repair repair = repairRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("삭제권한이 없거나 존재하지 않는 수리이력입니다."));
        repair.delete();
        return;
    }
}
