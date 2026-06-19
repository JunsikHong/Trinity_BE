package com.engineer.Trinity_BE.domain.airplane.repository;

import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirplaneTypeRepository extends JpaRepository<AirplaneType, Long> {
    boolean existsByName(String name);
    Optional<AirplaneType> findByName(String name);
}
