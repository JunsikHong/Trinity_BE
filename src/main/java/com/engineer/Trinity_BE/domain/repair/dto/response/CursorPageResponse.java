package com.engineer.Trinity_BE.domain.repair.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CursorPageResponse<T>(
        List<T> content,
        boolean haxNext,
        LocalDateTime nextCursorRepairAt,
        Long nexCursorId
) {
}
