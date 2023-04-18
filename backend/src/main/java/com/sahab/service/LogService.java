package com.sahab.service;


import com.sahab.Entity.LogWarningsEntity;
import com.sahab.Repository.LogWarningsRepositroy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogWarningsRepositroy repository;

    public LogService(LogWarningsRepositroy repository) {
        this.repository = repository;
    }

    public List<LogWarningsEntity> getAll() {

        return repository.findAll();
    }


}
