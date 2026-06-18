package com.engineer.Trinity_BE.domain.maintenance.service;

import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReportFile;
import com.engineer.Trinity_BE.domain.maintenance.exception.MaintenanceException;
import com.engineer.Trinity_BE.domain.maintenance.repository.MaintenanceReportFileRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.global.enums.FileExtension;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceReportFileService {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    private final MaintenanceReportFileRepository maintenanceReportFileRepository;
    private final MaintenanceReportFileStorageService maintenanceReportFileStorageService;

    @Transactional
    public List<MaintenanceReportFile> createFiles(MaintenanceReport report, List<MultipartFile> files) {
        if(files == null || files.isEmpty()) {
            return List.of();
        }

        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> createSingleFile(report, file))
                .toList();
    }

    private MaintenanceReportFile createSingleFile(MaintenanceReport report, MultipartFile multipartFile) {
        validateFile(multipartFile);

        String originalName = multipartFile.getOriginalFilename();
        String extension = extractExtension(originalName);
        FileExtension fileExtension = FileExtension.from(extension);

        if(fileExtension == null) {
            throw new MaintenanceException("이미지 파일만 등록할 수 있습니다. (허용 확장자: jpg, jpeg, png, gif, bmp, webp)");
        }

        MaintenanceReportFileStorageService.StoredFileInfo storedFileInfo = maintenanceReportFileStorageService.store(multipartFile, extension);

        MaintenanceReportFile file = MaintenanceReportFile.builder()
                .maintenanceReport(report)
                .originalName(originalName)
                .storedName(storedFileInfo.storedName())
                .filePath(storedFileInfo.filePath())
                .extension(extension.toLowerCase())
                .mimeType(fileExtension.getMimeType())
                .size(multipartFile.getSize())
                .build();

        return maintenanceReportFileRepository.save(file);
    }

    private void validateFile(MultipartFile multipartFile) {
        if(multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isBlank()) {
            throw new MaintenanceException("파일명이 올바르지 않습니다.");
        }

        if(multipartFile.getSize() > MAX_FILE_SIZE) {
            throw new MaintenanceException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }

    private String extractExtension(String originalName) {
        int dotIndex = originalName.lastIndexOf(".");
        if(dotIndex == -1 || dotIndex == originalName.length() -1) {
            throw new MaintenanceException("확장자가 없는 파일은 등록할 수 없습니다.");
        }
        return originalName.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
    }

    @Transactional
    public List<MaintenanceReportFile> findAllByReportId(Long reportId) {
        return maintenanceReportFileRepository.findAllByMaintenanceReportId(reportId);
    }

    @Transactional
    public MaintenanceReportFile getById(Long fileId) {
        return maintenanceReportFileRepository.findById(fileId)
                .orElseThrow(() -> new MaintenanceException("파일을 찾을 수 없습니다. id = " + fileId, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void deleteFile(Long fileId, CustomUserDetails userDetails) {
        MaintenanceReportFile file = getById(fileId);

        try {
            validateDeletePermission(file, userDetails);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
        }

        deleteInternal(file);
    }

    @Transactional
    public void deleteFilesByIds(List<Long> fileIds) {
        if(fileIds == null || fileIds.isEmpty()) {
            return;
        }

        for(Long fileId : fileIds) {
            MaintenanceReportFile file = getById(fileId);
            deleteInternal(file);
        }
    }

    @Transactional
    public void deleteAllByReport(MaintenanceReport report) {
        List<MaintenanceReportFile> files = maintenanceReportFileRepository.findAllByMaintenanceReportId(report.getId());
        for(MaintenanceReportFile file : files) {
            maintenanceReportFileStorageService.delete(file.getFilePath());
        }
        maintenanceReportFileRepository.deleteAllByMaintenanceReportId(report.getId());
    }

    private void deleteInternal(MaintenanceReportFile file) {
        maintenanceReportFileStorageService.delete(file.getFilePath());
        maintenanceReportFileRepository.delete(file);
    }

    private void validateDeletePermission(MaintenanceReportFile file, CustomUserDetails userDetails) throws AccessDeniedException {
        if("ADMIN".equals(userDetails.getUserRole())) {
            return;
        }

        User writer = file.getMaintenanceReport().getUser();
        if(!writer.getId().equals(userDetails.getUserId())) {
            throw new AccessDeniedException("본인이 등록한 파일만 삭제할 수 있습니다.");
        }
    }

}
