package com.engineer.Trinity_BE.domain.maintenance.initial;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import com.engineer.Trinity_BE.domain.maintenance.repository.MaintenanceReportRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceInitializer {

    private final MaintenanceReportRepository maintenanceReportRepository;
    private final UserRepository userRepository;
    private final AirplaneRepository airplaneRepository;

    @PostConstruct
    public void init() {
        if (maintenanceReportRepository.count() > 0) {
            return;
        }

        User admin = userRepository.findByEmail("admin@admin.com")
                .orElseThrow();

        Airplane hl1234 = airplaneRepository
                .findByRegistrationNumber("HL1234")
                .orElseThrow();

        Airplane hl2345 = airplaneRepository
                .findByRegistrationNumber("HL2345")
                .orElseThrow();

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl1234)
                        .chapter(52.0)
                        .station(540.0)
                        .description("좌측 동체 외판 스크래치 발견")
                        .build()
        );

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl1234)
                        .chapter(53.0)
                        .station(620.0)
                        .waterLine(180.0)
                        .description("외판 리벳 주변 부식 제거 및 재도장")
                        .build()
        );

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl1234)
                        .chapter(27.0)
                        .wingStation(350.0)
                        .rib(4.0)
                        .description("플랩 작동부 점검")
                        .build()
        );

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl2345)
                        .chapter(32.0)
                        .station(200.0)
                        .description("메인 랜딩기어 점검")
                        .build()
        );

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl2345)
                        .chapter(57.0)
                        .bodyStation(450.0)
                        .stringer(12.0)
                        .description("우측 날개 패널 손상 수리")
                        .build()
        );

        maintenanceReportRepository.save(
                MaintenanceReport.builder()
                        .user(admin)
                        .airplane(hl2345)
                        .chapter(21.0)
                        .frame(8.0)
                        .description("객실 공조 덕트 연결부 점검")
                        .build()
        );

    }
}
