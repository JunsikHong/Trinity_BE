package com.engineer.Trinity_BE.domain.maintenance.service;

import com.engineer.Trinity_BE.domain.maintenance.exception.MaintenanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
public class MaintenanceReportFileStorageService {

    private String uploadDir;

    public StoredFileInfo store(MultipartFile multipartFile, String extension) {
            String storedFileName = UUID.randomUUID() + "." + extension.toLowerCase();
            String datePath = LocalDate.now().toString().replace("-", "/");

            Path targetDir = Paths.get(uploadDir, "maintenance-report", datePath);
            Path targetPath = targetDir.resolve(storedFileName);

            try {
                Files.createDirectories(targetDir);
                multipartFile.transferTo(targetPath);
            } catch (IOException e) {
                log.error("파일 저장 실패: ", e);
                throw new MaintenanceException("파일 저장 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String relativePath = "maintenance-report/" + datePath + "/" + storedFileName;
            return new StoredFileInfo(storedFileName, relativePath);
    }

    public void delete(String filePath) {
        if(filePath == null || filePath.isBlank()) {
            return;
        }

        try {
            Path target = Paths.get(uploadDir, filePath);
            Files.deleteIfExists(target);
        } catch (IOException e) {
            log.warn("파일 삭제 실패 : {}", filePath, e);
        }

    }

    public record StoredFileInfo(String storedName, String filePath) {

    }
}
