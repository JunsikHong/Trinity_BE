package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterResponse;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.enums.ChapterType;
import com.engineer.Trinity_BE.domain.repair.repository.RepairChapterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairChapterService {

    private final RepairChapterRepository repairChapterRepository;

    public List<RepairChapter> getChaptersByAirplaneType(Long airplaneTypeId) {
        return repairChapterRepository.findByAirplaneTypeIdAndIsActiveOrderByChapterNumberAsc(airplaneTypeId);
    }

    public RepairChapter getChapter(Long repairChapterId) {
        return repairChapterRepository.findById(repairChapterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Chapter 입니다."));
    }

    public ChapterType resolveChapterType(RepairChapter repairChapter) {
        return ChapterType.valueOf(repairChapter.getChapterType());
    }
}
