package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepairRepository extends JpaRepository<Repair, Long> {

    @Query("select r from repair r join fetch r.user where r.airplane.id = :airplaneId order by r.createdAt desc")
    List<Repair> findAllByAirplaneIdWithUser(@Param("airplaneId") Long airplaneId);

    @Query("select r from Repair r join fetch r.user join fetch r.airplane where r.id = :id")
    Optional<Repair> findByIdWithUserAndAirplane(@Param("id") Long id);
}
