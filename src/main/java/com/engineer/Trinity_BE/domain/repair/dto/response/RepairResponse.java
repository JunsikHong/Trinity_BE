package com.engineer.Trinity_BE.domain.repair.dto.response;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;

import java.time.LocalDateTime;
import java.util.List;

public record RepairResponse(
        Long id,
        String description,
        LocalDateTime repairAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        List<LocationItem> locationItems,
        List<FileResponse> files

) {
    public static RepairResponse from (Repair repair, List<RepairFile> files) {
        return new RepairResponse(
                repair.getId(),
                repair.getDescription(),
                repair.getRepairAt(),
                repair.getCreatedAt(),
                repair.getUpdatedAt(),

                repair.getRepairLocationItems().stream()
                        .map(LocationItem::from)
                        .toList(),

                files == null ? List.of() : files.stream().map(FileResponse::from).toList()
        );
    }

    public record FileResponse(
            Long id,
            String originalName,
            String storedName,
            String mimeType,
            Long size
    ) {
        public static FileResponse from(RepairFile file) {
            return new FileResponse(
                    file.getId(),
                    file.getOriginalName(),
                    file.getStoredName(),
                    file.getMimeType(),
                    file.getSize()
            );
        }

    }

    public record LocationItem(
            Long locationId,
            String locationName,
            String locationCode,

            Long chapterId,
            Integer chapterNumber,
            String chapterName,

            String value
    ) {
        public static LocationItem from (RepairLocationItem item) {
            return new LocationItem(
                    item.getRepairLocation().getId(),
                    item.getRepairLocation().getName(),
                    item.getRepairLocation().getCode(),
                    item.getRepairLocation().getRepairChapter().getId(),
                    item.getRepairLocation().getRepairChapter().getChapterNumber(),
                    item.getRepairLocation().getRepairChapter().getChapterName(),

                    item.getValue()
            );
        }
    }
}
