package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocation;
import com.engineer.Trinity_BE.domain.repair.entity.RepairLocationItem;
import com.engineer.Trinity_BE.domain.repair.repository.RepairLocationItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairLocationItemService {

    private final RepairLocationItemRepository repairLocationItemRepository;

    @Transactional
    public List<RepairLocationItem> saveLocations(Repair repair, List<RepairLocation> locations) {
        List<RepairLocationItem> items = locations.stream().map(location -> RepairLocationItem.builder()
                .repair(repair)
                .repairLocation(location)
                .build())
                .toList();
        return repairLocationItemRepository.saveAll(items);
    }

    public List<RepairLocationItem> getByRepair(Long repairId) {
        return repairLocationItemRepository.findByRepairId(repairId);
    }

    @Transactional
    public void deleteByRepair(Long repairId) {
        repairLocationItemRepository.deleteByRepairId(repairId);
    }
}
