package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairStatRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairStatItem;
import com.engineer.Trinity_BE.domain.repair.enums.StatUnit;

import java.time.LocalDateTime;
import java.util.List;

public interface RepairStatRepositoryCustom {

    List<RepairStatItem> findStatistics(
            RepairStatRequest request,
            LocalDateTime from,
            StatUnit unit
    );
}
