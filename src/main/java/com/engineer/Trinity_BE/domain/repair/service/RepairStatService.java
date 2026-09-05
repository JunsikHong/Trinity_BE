package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairStatRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairStatItem;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairStatResponse;
import com.engineer.Trinity_BE.domain.repair.enums.StatPeriod;
import com.engineer.Trinity_BE.domain.repair.enums.StatUnit;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairStatRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairStatService {

    private final RepairStatRepositoryCustomImpl repairStatRepositoryCustom;

    public RepairStatResponse getStatistics(RepairStatRequest request) {
        validateRequest(request);

        LocalDateTime from = calculateFrom(request.period());

        StatUnit unit = calculateStatUnit(request.period());

        List<RepairStatItem> items = repairStatRepositoryCustom.findStatistics(request, from, unit);

        return RepairStatResponse.of(request, unit, items);
    }

    private LocalDateTime calculateFrom(StatPeriod period) {
        LocalDateTime now = LocalDateTime.now();

        return switch (period) {
            case ALL -> null;
            case MONTH_1 ->  now.minusMonths(1);
            case MONTH_3 -> now.minusMonths(3);
            case MONTH_6 -> now.minusMonths(6);
            case YEAR_1 -> now.minusYears(1);
        };
    }

    private StatUnit calculateStatUnit(StatPeriod period) {
        return switch (period) {
            case ALL, MONTH_3, MONTH_6, YEAR_1 -> StatUnit.MONTH;
            case MONTH_1 -> StatUnit.WEEK;
        };
    }

    private void validateRequest(RepairStatRequest request) {
        switch (request.type()) {
            case ALL -> {

            }

            case AIRPLANE_TYPE -> {
                if(request.airplaneTypeId() == null) {
                    throw new IllegalArgumentException("기종 ID가 필요합니다.");
                }
            }

            case AIRPLANE -> {
                if(request.airplaneTypeId() == null || request.airplaneId() == null) {
                    throw new IllegalArgumentException("기종 ID와 비행기 ID가 필요합니다.");
                }
            }

            case CHAPTER -> {
                if(request.airplaneTypeId() == null || request.airplaneId() == null || request.chapter() == null) {
                    throw new IllegalArgumentException("기종 ID, 비행기 ID, 챕터가 필요합니다.");
                }
            }
        }
    }

}
