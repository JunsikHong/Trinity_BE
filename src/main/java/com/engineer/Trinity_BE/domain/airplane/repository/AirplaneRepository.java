package com.engineer.Trinity_BE.domain.airplane.repository;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
