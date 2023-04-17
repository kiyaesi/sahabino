package com.sahab.Repository;

import com.sahab.Entity.LogWarningsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogWarningsRepositroy extends JpaRepository<LogWarningsEntity, Long> {

    @Query(value = "SELECT log FROM LogWarningsEntity log WHERE log.warningType = :warningType AND log.system = :system ORDER BY log.logCreatedAt DESC")
    List<LogWarningsEntity> findLogWarningsByWarningTypeSortByTime(@Param("warningType") String warningType, @Param("system") String System, Pageable Pageable);

    @Query(value = "SELECT log FROM LogWarningsEntity log WHERE log.system = :system ORDER BY log.logCreatedAt DESC")
    List<LogWarningsEntity> findLogWarningsBySystemSortByTime(@Param("system")String System, Pageable Pageable);

}
