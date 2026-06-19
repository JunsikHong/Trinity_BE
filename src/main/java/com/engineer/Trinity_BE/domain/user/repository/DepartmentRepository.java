package com.engineer.Trinity_BE.domain.user.repository;

import com.engineer.Trinity_BE.domain.user.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);
    Optional<Department> findByName(String name);
}
