package com.engineer.Trinity_BE.repair;

import com.engineer.Trinity_BE.domain.repair.dto.response.RepairDetailResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairLocationResponse;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;
import com.engineer.Trinity_BE.domain.repair.enums.ChapterType;
import com.engineer.Trinity_BE.domain.repair.service.RepairLocationItemService;
import com.engineer.Trinity_BE.domain.repair.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RepairDetail {

    private final RepairService repairService;
    private final RepairLocationItemService repairLocationItemService;
    private final RepairFuselageService repairFuselageService;

    public RepairDetailResponse getRepairDetail(Long repairId) {
        Repair repair = repairService.getRepair(repairId);
        List<RepairLocationItem> locationItems = repairLocationItemService.getByRepair(repairId);
        RepairFuselage fuselage = repairFuselageService.getByRepair(repairId);

        String chapterType = null;
        List<RepairLocationResponse> repairLocationResponses = null;
        RepairFuselageResponse repairFuselageResponse = null;

        if(!locationItems.isEmpty()) {
            chapterType = ChapterType.LOCATION.name();
            repairLocationResponses = locationItems.stream().map(item -> RepairLocationResponse.from(item.getRepairLocation())).toList();
        } else if (fuselage != null) {
            chapterType = ChapterType.FUSELAGE.name();
            repairFuselageResponse = RepairFuselageResponse.from(fuselage);
        }

        return new RepairDetailResponse(
                repair.getId(),
                repair.getAirplane().getId(),
                repair.getDescription(),
                chapterType,
                repairLocationResponses,
                repairFuselageResponse,
                repair.getCreatedAt(),
                repair.getUpdatedAt()
        );
    }
}
