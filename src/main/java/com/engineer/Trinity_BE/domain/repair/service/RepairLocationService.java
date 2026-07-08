package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.repository.RepairChapterRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairLocationService {

    private final RepairLocationRepository repairLocationRepository;

    public List<RepairLocation> getLocationsByChapter(Long repairChapterId) {
        return repairLocationRepository.findByRepairChapterIdAndIsActiveTrueOrderByIdAsc(repairChapterId);
    }

    public List<RepairLocation> getLocationsByIds(List<Long> locationIds) {
        List<RepairLocation> locations = repairLocationRepository.findAllById(locationIds);
        if(locations.size() != locationIds.size()) {
            throw new EntityNotFoundException("존재하지 않는 Location 이 포함되어 있습니다.");
        }
        return locations;
    }
}
