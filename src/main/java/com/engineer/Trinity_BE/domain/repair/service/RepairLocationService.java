package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairLocationRequest;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairLocationService {

    private final RepairLocationRepository repairLocationRepository;

    public List<RepairLocation> findAllByRepairChapterId(Long repairChapterId) {
        return repairLocationRepository.findAllByRepairChapterIdAndIsActiveTrue(repairChapterId);
    }

    public List<RepairLocation> findAll() {
        return repairLocationRepository.findAllWithAirplaneType();
    }

    @Transactional
    public void create(RepairLocationRequest request, RepairChapter repairChapter) {
        RepairLocation repairLocation = RepairLocation.builder()
                .repairChapter(repairChapter)
                .name(request.name())
                .code(request.code())
                .section(request.section())
                .inputType(request.inputType())
                .sortOrder(request.sortOrder())
                .isActive(request.isActive())
                .build();
        repairLocationRepository.save(repairLocation);
    }

    @Transactional
    public void update(Long id, RepairLocationRequest request, RepairChapter repairChapter) {
        RepairLocation repairLocation = repairLocationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Repair Location을 찾을 수 없습니다."));

        repairLocation.update(
                repairChapter,
                request.name(),
                request.code(),
                request.section(),
                request.inputType(),
                request.sortOrder(),
                request.isActive()
        );
    }

    @Transactional
    public void delete(Long id) {
        RepairLocation repairLocation = repairLocationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Repair Location을 찾을 수 없습니다."));

        repairLocationRepository.deleteById(id);
    }
}
