package com.sahab.Repository;

import com.sahab.Entity.LogWarningsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LogWarningsRepositroy extends JpaRepository<LogWarningsEntity, Long> {

    @Query(value = "SELECT u BY LogWarningsEntity u WHERE u.warningType = ?1 AND u.system = ?2 ORDER BY logCreatedAt DESC")
    List<LogWarningsEntity> findLogWarningsByWarningTypeSortByTime(String warningType,String System,Pageable Pageable);

    @Query(value = "SELECT u BY LogWarningsEntity u WHERE u.system = ?1 ORDER BY logCreatedAt DESC")
    List<LogWarningsEntity> findLogWarningsBySystemSortByTime(String System, Pageable Pageable);

    @Query(value = "SELECT u BY LogWarningsEntity u WHERE u.system = ?1 and u.logCreatedAt between ?2  and ?3 ")
    List<LogWarningsEntity> findLogWarningsBySystemByTime(String System, LocalDateTime logCreatedAt, LocalDateTime StartTime);

}
