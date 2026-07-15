package com.engineer.Trinity_BE.domain.repair.initial;

import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.repository.RepairChapterRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepairLocationInitializer {

    private final RepairChapterRepository repairChapterRepository;
    private final RepairLocationRepository repairLocationRepository;

    @PostConstruct
    public void init() {
        RepairChapter repairChapter;

    }
}
