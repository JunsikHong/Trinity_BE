package com.engineer.Trinity_BE.domain.repair.entity;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
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
@Table(name = "repair_chapters")
public class RepairChapter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_type_id", nullable = false)
    private AirplaneType airplaneType;

    @Column(name = "chapter_number")
    private Integer chapterNumber;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "is_active")
    private boolean isActive;
}
