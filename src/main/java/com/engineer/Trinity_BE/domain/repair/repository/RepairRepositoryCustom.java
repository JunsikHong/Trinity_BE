package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairSearchRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface RepairRepositoryCustom {
    List<Long> findPageIds(Long airplaneId, RepairSearchRequest request, LocalDateTime cursorValue, Long cursorId, int size);
}
