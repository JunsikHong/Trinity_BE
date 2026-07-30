package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairFile;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RepairFileService {

    private final RepairFileRepository repairFileRepository;

    @Value("${file.upload-path}")
    private String uploadPath;

    public void create(Repair repair, List<MultipartFile> files) throws IOException {
        if(files == null || files.isEmpty()) return;

        Path directory = Paths.get(uploadPath);

        if(Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        for(MultipartFile file : files) {
            if(file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);
            String storedName = UUID.randomUUID() + extension;
            Path target = directory.resolve(storedName);
            file.transferTo(target);

            RepairFile repairFile = RepairFile.builder()
                    .repair(repair)
                    .originalName(originalName)
                    .storedName(storedName)
                    .filePath(target.toString())
                    .extension(extension)
                    .mimeType(file.getContentType())
                    .size(file.getSize())
                    .build();

            repairFileRepository.save(repairFile);
        }
    }

    public void delete(List<Long> ids) throws IOException {
        if(ids == null || ids.isEmpty()) return;

        List<RepairFile> files = repairFileRepository.findAllById(ids);

        for(RepairFile file : files) {
            Files.deleteIfExists(Paths.get(file.getFilePath()));
        }

        repairFileRepository.deleteAll(files);
    }

    private String getExtension(String filename) {
        if(filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf("."));
    }

}
