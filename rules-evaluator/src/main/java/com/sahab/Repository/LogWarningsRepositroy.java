package com.sahab.Repository;

import com.sahab.Entity.LogWarningsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface LogWarningsRepositroy extends JpaRepository<LogWarningsEntity, Long> {

    @Query(value = "SELECT u BY LogWarningsEntity u WHERE u.warningType = ?1 ORDER BY logCreatedAt ")
    List<LogWarningsEntity> findLogWarningsByWarningTypeSortByTime(String warningType,Pageable Pageable);

    @Query(value = "SELECT u BY LogWarningsEntity u WHERE u.system = ?1 ORDER BY logCreatedAt ")
    List<LogWarningsEntity> findLogWarningsBySystemSortByTime(String warningType,Pageable Pageable);
}
