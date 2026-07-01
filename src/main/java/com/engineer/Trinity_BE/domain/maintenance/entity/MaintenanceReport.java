package com.engineer.Trinity_BE.domain.maintenance.entity;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "maintenance_reports")
public class MaintenanceReport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id",  nullable = false)
    private Airplane airplane;

    // 정비 매뉴얼 분류 번호
    @Column(name = "chapter")
    private Double chapter;

    // 기수에서 꼬리방향 (x축) 위치
    @Column(name = "station")
    private Double station;

    // 아래에서 위 방향 (z축) 위치
    @Column(name = "water_line")
    private Double waterLine;

    // 항공기 중심선 기준 좌우 (y축) 위치
    @Column(name = "buttock_line")
    private Double buttockLine;

    // 동체 길이 방향 보강제 번호
    @Column(name = "stringer")
    private Double stringer;

    // 동체 단면 방향 구조물 번호
    @Column(name = "frame")
    private Double frame;

    // 날개 내부 단면 구조물 번호
    @Column(name = "rib")
    private Double rib;

    // 날개 방향 위치
    @Column(name = "wing_station")
    private Double wingStation;

    // 동체 방향 위치
    @Column(name = "body_station")
    private Double bodyStation;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(Airplane airplane,
                       Double chapter,
                       Double station,
                       Double waterLine,
                       Double buttockLine,
                       Double stringer,
                       Double frame,
                       Double rib,
                       Double wingStation,
                       Double bodyStation,
                       String description) {
        this.airplane = airplane;
        this.chapter = chapter;
        this.station = station;
        this.waterLine = waterLine;
        this.buttockLine = buttockLine;
        this.stringer = stringer;
        this.frame = frame;
        this.rib = rib;
        this.wingStation = wingStation;
        this.bodyStation = bodyStation;
        this.description = description;
    }

}
