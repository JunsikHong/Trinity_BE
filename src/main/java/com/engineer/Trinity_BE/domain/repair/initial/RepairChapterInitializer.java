package com.engineer.Trinity_BE.domain.repair.initial;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.repository.RepairChapterRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepairChapterInitializer {

    private final AirplaneTypeRepository airplaneTypeRepository;
    private final RepairChapterRepository repairChapterRepository;

    @PostConstruct
    public void init() {
        AirplaneType airplaneType;
        if(!airplaneTypeRepository.existsByName("B737-800")) {
            airplaneType = airplaneTypeRepository.save(
                    AirplaneType.builder()
                            .name("B737-800")
                            .build()
            );
        } else {
            airplaneType = airplaneTypeRepository.findByName("B737-800")
                    .orElseThrow();
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 52)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(52)
                            .chapterName("door")
                            .isActive(true)
                            .build()
            );
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 53)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(53)
                            .chapterName("fuselage")
                            .isActive(true)
                            .build()
            );
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 54)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(54)
                            .chapterName("naccel")
                            .isActive(true)
                            .build()
            );
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 55)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(55)
                            .chapterName("stabilizer")
                            .isActive(true)
                            .build()
            );
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 56)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(56)
                            .chapterName("window")
                            .isActive(false)
                            .build()
            );
        }

        if(!repairChapterRepository.existsByAirplaneTypeIdAndChapterNumber(airplaneType.getId(), 57)) {
            repairChapterRepository.save(
                    RepairChapter.builder()
                            .airplaneType(airplaneType)
                            .chapterNumber(57)
                            .chapterName("wing")
                            .isActive(true)
                            .build()
            );
        }


    }
}
