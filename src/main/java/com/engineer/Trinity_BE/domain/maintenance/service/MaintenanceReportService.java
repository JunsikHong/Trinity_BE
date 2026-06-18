package com.engineer.Trinity_BE.domain.maintenance.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.maintenance.dto.request.MaintenanceReportRequest;
import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportDetailResponse;
import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportFileResponse;
import com.engineer.Trinity_BE.domain.maintenance.dto.response.MaintenanceReportListResponse;
import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReportFile;
import com.engineer.Trinity_BE.domain.maintenance.exception.MaintenanceException;
import com.engineer.Trinity_BE.domain.maintenance.repository.MaintenanceReportRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceReportService {

    private final MaintenanceReportRepository maintenanceReportRepository;
    private final UserRepository userRepository;
    private final AirplaneRepository airplaneRepository;
    private final MaintenanceReportFileService maintenanceReportFileService;

    @Transactional
    public MaintenanceReportDetailResponse create(MaintenanceReportRequest request, List<MultipartFile> files, CustomUserDetails userDetails) {
        User writer = getUserOrThrow(userDetails.getUserId());
        Airplane airplane = getAirplaneOrThrow(request.getAirplaneId());

        MaintenanceReport report = MaintenanceReport.builder()
                .user(writer)
                .airplane(airplane)
                .chapter(request.getChapter())
                .station(request.getStation())
                .waterLine(request.getWaterLine())
                .buttockLine(request.getButtockLine())
                .stringer(request.getStringer())
                .frame(request.getFrame())
                .rib(request.getRib())
                .wingStation(request.getWingStation())
                .bodyStation(request.getBodyStation())
                .description(request.getDescription())
                .build();

        MaintenanceReport saved = maintenanceReportRepository.save(report);
        maintenanceReportFileService.createFiles(saved, files);
        return getDetail(report.getId());
    }

    @Transactional
    public MaintenanceReportDetailResponse update(Long reportId, MaintenanceReportRequest request, List<MultipartFile> newFiles, CustomUserDetails userDetails) {
        MaintenanceReport report = maintenanceReportRepository.findByIdWithUserAndAirplane(reportId)
                .orElseThrow(() -> new MaintenanceException("정비 이력을 찾을 수 없습니다. id=" + reportId, HttpStatus.NOT_FOUND));

        try {
            validateWritePermission(report, userDetails);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
        }

        Airplane airplane = getAirplaneOrThrow(request.getAirplaneId());

        report.update(
                airplane,
                request.getChapter(),
                request.getStation(),
                request.getWaterLine(),
                request.getButtockLine(),
                request.getStringer(),
                request.getFrame(),
                request.getRib(),
                request.getWingStation(),
                request.getBodyStation(),
                request.getDescription()
        );

        if(request.getDeleteFileIds() != null && !request.getDeleteFileIds().isEmpty()) {
            maintenanceReportFileService.deleteFilesByIds(request.getDeleteFileIds());
        }

        if(newFiles != null && !newFiles.isEmpty()) {
            maintenanceReportFileService.createFiles(report, newFiles);
        }

        return getDetail(report.getId());
    }

    @Transactional
    public void delete(Long reportId, CustomUserDetails userDetails) {
        MaintenanceReport report = maintenanceReportRepository.findByIdWithUserAndAirplane(reportId)
                .orElseThrow(() -> new MaintenanceException("정비 이력을 찾을 수 없습니다. id=" + reportId, HttpStatus.NOT_FOUND));

        try {
            validateWritePermission(report, userDetails);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
        }

        maintenanceReportFileService.deleteAllByReport(report);
        maintenanceReportRepository.delete(report);
    }

    @Transactional
    public List<MaintenanceReportListResponse> findAll() {
        return maintenanceReportRepository.findAllWithUser()
                .stream()
                .map(MaintenanceReportListResponse::from)
                .toList();
    }

    @Transactional
    public MaintenanceReportDetailResponse getDetail(Long reportId) {
        MaintenanceReport report = maintenanceReportRepository.findByIdWithUserAndAirplane(reportId)
                .orElseThrow(() -> new MaintenanceException("정비 이력을 찾을 수 없습니다. id=" + reportId, HttpStatus.NOT_FOUND));

        List<MaintenanceReportFile> files = maintenanceReportFileService.findAllByReportId(reportId);
        List<MaintenanceReportFileResponse> fileResponses = files.stream()
                .map(MaintenanceReportFileResponse::from)
                .toList();

        return MaintenanceReportDetailResponse.of(report, fileResponses);
    }

    private void validateWritePermission(MaintenanceReport report, CustomUserDetails userDetails) throws AccessDeniedException {
        if("ADMIN".equals(userDetails.getUserRole())) {
            return;
        }

        if(!report.getUser().getId().equals(userDetails.getUserId())) {
            throw new AccessDeniedException("본인이 작성한 정비 이력만 수정/삭제 할 수 있습니다.");
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new MaintenanceException("사용자를 찾을 수 없습니다. id=" + userId, HttpStatus.NOT_FOUND));
    }

    private Airplane getAirplaneOrThrow(Long airplaneId) {
        return airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new MaintenanceException("항공기를 찾을 수 없습니다. id=" + airplaneId, HttpStatus.NOT_FOUND));
    }
}
