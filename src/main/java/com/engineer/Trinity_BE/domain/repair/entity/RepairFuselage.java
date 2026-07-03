package com.engineer.Trinity_BE.domain.repair.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repair_fuselages")
public class RepairFuselage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_chapter_id", nullable = false)
    private RepairChapter repairChapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_id" ,nullable = false)
    private Repair repair;

    @Column(name = "station_start")
    private Double stationStart;

    @Column(name = "station_end")
    private Double stationEnd;

    @Column(name = "stringer_start")
    private Double stringerStart;

    @Column(name = "stringer_end")
    private Double stringerEnd;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime cratedAt;
}
