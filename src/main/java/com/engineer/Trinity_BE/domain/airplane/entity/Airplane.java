package com.engineer.Trinity_BE.domain.airplane.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_type_id")
    private AirplaneType airplaneType;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

}
