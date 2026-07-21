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
@Table(name = "repair_locations")
public class RepairLocation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_chapter_id", nullable = false)
    private RepairChapter repairChapter;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "section")
    private Integer section;

    @Column(name = "input_type")
    private String inputType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_active")
    private boolean isActive;

    public void update(
            RepairChapter repairChapter,
            String name,
            String code,
            Integer section,
            String inputType,
            Integer sortOrder,
            boolean isActive
    ) {
        this.repairChapter = repairChapter;
        this.name = name;
        this.code = code;
        this.section = section;
        this.inputType = inputType;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
    }

}
