package com.engineer.Trinity_BE.domain.airplane.repository;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);
}
