package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairStatService {

    private final RepairRepository repairRepository;


}
