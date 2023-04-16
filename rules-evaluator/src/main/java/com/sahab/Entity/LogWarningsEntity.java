package com.sahab.Entity;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "logWarningsEntity")
@Table(name = "LOGWARNING")
public class LogWarningsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String warningName;
    @NotNull
    private String description;

    private String warningType;
    @NotNull
    private LocalDateTime logCreatedAt;
    @NotNull
    private String system;
    @NotNull
    private LocalDateTime fileCreatedAt;

    public LogWarningsEntity(String warningName,String warningType,String description,LocalDateTime logCreatedAt,String system,LocalDateTime fileCreatedAt) {
    this.warningName = warningName;
    this.warningType = warningType;
    this.description = description;
    this.logCreatedAt= logCreatedAt;
    this.system = system;
    this.fileCreatedAt= fileCreatedAt;
    }
}