package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RepairFileService {

    private final RepairFileRepository repairFileRepository;
    private final RepairFileStorageService repairFileStorageService;

    public void create(Repair repair, List<MultipartFile> files) throws IOException {
        if(files == null || files.isEmpty()) return;

        for(MultipartFile file : files) {
            if(file.isEmpty()) continue;

            String storedName = repairFileStorageService.store(file);
            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);

            RepairFile repairFile = RepairFile.builder()
                    .repair(repair)
                    .originalName(originalName)
                    .storedName(storedName)
                    .filePath(storedName)
                    .extension(extension)
                    .mimeType(file.getContentType())
                    .size(file.getSize())
                    .build();

            repairFileRepository.save(repairFile);
        }
    }

    public void delete(List<Long> fileIds) throws IOException {
        if (fileIds == null || fileIds.isEmpty()) {
            return;
        }

        List<RepairFile> files = repairFileRepository.findAllById(fileIds);

        for (RepairFile file : files) {
            repairFileStorageService.delete(file.getStoredName());
        }

        repairFileRepository.deleteAll(files);
    }

    public void deleteByRepair(Long repairId) throws IOException {

        List<RepairFile> files = repairFileRepository.findByRepairId(repairId);

        for (RepairFile file : files) {
            repairFileStorageService.delete(file.getStoredName());
        }

        repairFileRepository.deleteAll(files);
    }

    public Map<Long, RepairFile> findFirstFiles(List<Long> repairIds) {

        return repairFileRepository.findByRepairIdInOrderByIdAsc(repairIds)
                .stream()
                .collect(Collectors.toMap(
                        file -> file.getRepair().getId(),
                        file -> file,
                        (first, second) -> first
                ));
    }

    public List<RepairFile> findAll(Long repairId) {
        return repairFileRepository.findByRepairId(repairId);
    }

    public RepairFile findOne(Long fileId) {
        return repairFileRepository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("파일을 찾을 수 없습니다."));
    }

    private String getExtension(String filename) {
        if(filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf("."));
    }

}
