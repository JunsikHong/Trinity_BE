package com.engineer.Trinity_BE.repair;

import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterInputResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairChapterResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairLocationResponse;
import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import com.engineer.Trinity_BE.domain.repair.enums.ChapterType;
import com.engineer.Trinity_BE.domain.repair.service.RepairChapterService;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RepairRead {

    private final RepairChapterService repairChapterService;
    private final RepairLocationService repairLocationService;

    // 기종 선택 시 해당 기종의 chapter 목록 조회
    public List<RepairChapterResponse> getChapters(Long airplaneTypeId) {
        return repairChapterService.getChaptersByAirplaneType(airplaneTypeId).stream()
                .map(RepairChapterResponse::from)
                .toList();
    }

    public RepairChapterInputResponse getChapterInput(Long repairChapterId) {
        RepairChapter chapter = repairChapterService.getChapter(repairChapterId);
        ChapterType chapterType = repairChapterService.resolveChapterType(chapter);

        if(chapterType == ChapterType.LOCATION) {
            List<RepairLocationResponse> locationResponses = repairLocationService.getLocationsByChapter(repairChapterId)
                    .stream()
                    .map(RepairLocationResponse::from)
                    .toList();
            return new RepairChapterInputResponse(chapter.getId(), chapterType.name(), locationResponses);
        }

        return new RepairChapterInputResponse(chapter.getId(), chapterType.name(), null);
    }
}
