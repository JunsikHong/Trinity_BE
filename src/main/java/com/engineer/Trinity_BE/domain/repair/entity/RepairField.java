package com.engineer.Trinity_BE.domain.repair.entity;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
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
@Table(name = "repair_fields")
public class RepairField {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_type_id", nullable = false)
    private AirplaneType airplaneType;

    @Column(name = "chapter")
    private Integer chapter;

    @Column(name = "field_label")
    private String fieldLabel;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "field_options", columnDefinition = "TEXT")
    private String fieldOptions;

    @Column(name = "is_required")
    private boolean isRequired;

    @Column(name = "field_order")
    private Integer fieldOrder;

    @Column(name = "is_active")
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
