package com.engineer.Trinity_BE.domain.repair.controller;

import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import com.engineer.Trinity_BE.domain.repair.service.RepairFileService;
import com.engineer.Trinity_BE.domain.repair.service.RepairFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair-file")
public class RepairFileController {

    private final RepairFileStorageService repairFileStorageService;
    private final RepairFileService repairFileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getFile(
            @PathVariable Long fileId
    ) throws IOException {
        RepairFile file = repairFileService.findOne(fileId);
        Resource resource = repairFileStorageService.load(file.getStoredName());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMimeType())).body(resource);
    }
}
