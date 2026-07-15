package com.engineer.Trinity_BE.domain.repair.repository;

import com.engineer.Trinity_BE.domain.repair.entity.RepairChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairChapterRepository extends JpaRepository<RepairChapter, Long> {

    List<RepairChapter> findAllByAirplaneTypeIdAndIsActiveTrue(Long airplaneTypeId);

    boolean existsByAirplaneTypeIdAndChapterNumber(Long airplaneTypeId, Integer chapterNumber);
}
