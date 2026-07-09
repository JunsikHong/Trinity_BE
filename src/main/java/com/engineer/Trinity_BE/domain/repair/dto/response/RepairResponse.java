package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;

import javax.xml.stream.Location;
import java.time.LocalDateTime;
import java.util.List;

public record RepairResponse(
        Long id,
        String description,
        LocalDateTime repairAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        List<LocationItem> locationItems
) {
    public static RepairResponse from (Repair repair) {
        return new RepairResponse(
                repair.getId(),
                repair.getDescription(),
                repair.getRepairAt(),
                repair.getCreatedAt(),
                repair.getUpdatedAt(),

                repair.getRepairLocationItems().stream()
                        .map(LocationItem::from)
                        .toList()
        );
    }

    public record LocationItem(
            Long locationId,
            String locationName,

            Long chapterId,
            Integer chapterNumber,
            String chapterName,

            String value
    ) {
        public static LocationItem from (RepairLocationItem item) {
            return new LocationItem(
                    item.getRepairLocation().getId(),
                    item.getRepairLocation().getName(),
                    item.getRepairLocation().getRepairChapter().getId(),
                    item.getRepairLocation().getRepairChapter().getChapterNumber(),
                    item.getRepairLocation().getRepairChapter().getChapterName(),

                    item.getValue()
            );
        }
    }
}
