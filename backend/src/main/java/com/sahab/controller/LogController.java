package com.sahab.controller;


import com.sahab.Entity.LogWarningsEntity;
import com.sahab.service.LogMapper;
import com.sahab.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;
    private final LogMapper logMapper;

    public LogController(LogService logService, LogMapper logMapper) {
        this.logService = logService;
        this.logMapper = logMapper;
    }

    @GetMapping
    public List<LogWarningsEntity> getLogs() {
        return logService.getAll();
    }

}
