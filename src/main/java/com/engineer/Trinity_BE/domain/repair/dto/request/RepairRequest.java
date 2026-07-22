package com.engineer.Trinity_BE.domain.repair.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RepairRequest(
        @NotNull Long airplaneId,
        @NotNull Long repairChapterId,
        String description,
        LocalDate repairAt,
        @NotEmpty List<RepairLocationItemRequest> locations
) {
    public record RepairLocationItemRequest(
            @NotNull Long locationId,
            @NotBlank String value
    ) {
    }
}
