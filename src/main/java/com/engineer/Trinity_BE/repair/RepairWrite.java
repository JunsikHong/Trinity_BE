package com.engineer.Trinity_BE.repair;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.service.AirplaneService;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairCreateRequest;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairUpdateRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairResponse;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.enums.ChapterType;
import com.engineer.Trinity_BE.domain.repair.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RepairWrite {

    private final RepairService repairService;
    private final RepairChapterService repairChapterService;
    private final RepairLocationService repairLocationService;
    private final RepairLocationItemService repairLocationItemService;
    private final RepairFuselageService repairFuselageService;
    private final AirplaneService airplaneService;

    @Transactional
    public RepairResponse createRepair(Long userId, RepairCreateRequest request) {
        Airplane airplane = airplaneService.getAirplane(request.airplaneId());
        Repair repair = repairService.createRepair(airplane, request.description());
        // 유저 추가
        saveChapterData(
                repair,
                request.repairChapterId(),
                request.chapterType(),
                request.locationIds(),
                request.stationStart(),
                request.stationEnd(),
                request.stringerStart(),
                request.stringerEnd()
        );
        return RepairResponse.from(repair);
    }

    @Transactional
    public RepairResponse updateRepair(Long repairId, RepairUpdateRequest request) {
        Repair repair = repairService.updateDescription(repairId, request.description());

        repairLocationItemService.deleteByRepair(repairId);
        repairFuselageService.deleteByRepair(repairId);

        saveChapterData(
                repair,
                request.repairChapterId(),
                request.chapterType(),
                request.locationIds(),
                request.stationStart(),
                request.stationEnd(),
                request.stringerStart(),
                request.stringerEnd()
        );

        return RepairResponse.from(repair);
    }

    @Transactional
    public void deleteRepair(Long repairId) {
        repairLocationItemService.deleteByRepair(repairId);
        repairFuselageService.deleteByRepair(repairId);
        repairService.deleteRepair(repairId);
    }

    private void saveChapterData(Repair repair, Long repairChapterId, String chapterTypeValue,
                                 List<Long> locationIds,
                                 Double stationStart, Double stationEnd,
                                 Double stringerStart, Double stringerEnd) {
        RepairChapter repairChapter = repairChapterService.getChapter(repairChapterId);
        ChapterType chapterType = ChapterType.valueOf(chapterTypeValue);

        if(chapterType == ChapterType.LOCATION) {
            List<RepairLocation> locations = repairLocationService.getLocationsByIds(locationIds);
            repairLocationItemService.saveLocations(repair, locations);
            return;
        }

        repairFuselageService.save(repair, repairChapter, stationStart, stationEnd, stringerStart, stringerEnd);
    }
}
