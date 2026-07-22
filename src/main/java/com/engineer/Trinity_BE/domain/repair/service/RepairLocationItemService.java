package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.dto.request.RepairRequest;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationItemRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepairLocationItemService {

    private final RepairLocationItemRepository repairLocationItemRepository;
    private final RepairLocationRepository repairLocationRepository;

    @Transactional
    public void create(Repair repair, RepairRequest request) {
        List<Long> locationIds = request.locations().stream()
                .map(RepairRequest.RepairLocationItemRequest::locationId)
                .toList();

        Map<Long, RepairLocation> repairLocationMap = repairLocationRepository.findAllById(locationIds).stream()
                .collect(Collectors.toMap(RepairLocation::getId, Function.identity()));

        List<RepairLocationItem> repairLocationItems = request.locations().stream()
                .map(repairLocationItemRequest -> {
                    RepairLocation repairLocation = repairLocationMap.get(repairLocationItemRequest.locationId());
                    if(repairLocation == null) {
                        throw new EntityNotFoundException("존재하지 않는 repair location 입니다.");
                    }
                    return RepairLocationItem.builder()
                            .repair(repair)
                            .repairLocation(repairLocation)
                            .value(repairLocationItemRequest.value())
                            .build();
                }).toList();

        repairLocationItemRepository.saveAll(repairLocationItems);
    }

    @Transactional
    public void update(Repair repair, RepairRequest request) {
        repairLocationItemRepository.deleteByRepairId(repair.getId());
        List<Long> locationIds = request.locations().stream()
                .map(RepairRequest.RepairLocationItemRequest::locationId)
                .toList();

        Map<Long, RepairLocation> repairLocationMap = repairLocationRepository.findAllById(locationIds).stream()
                .collect(Collectors.toMap(RepairLocation::getId, Function.identity()));

        List<RepairLocationItem> repairLocationItems = request.locations().stream()
                .map(repairLocationItemRequest -> {
                    RepairLocation repairLocation = repairLocationMap.get(repairLocationItemRequest.locationId());
                    if(repairLocation == null) {
                        throw new EntityNotFoundException("존재하지 않는 repair location 입니다.");
                    }

                    return RepairLocationItem.builder()
                            .repair(repair)
                            .repairLocation(repairLocation)
                            .value(repairLocationItemRequest.value())
                            .build();
                }).toList();
        repairLocationItemRepository.saveAll(repairLocationItems);
    }

}
