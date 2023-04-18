package com.sahab.Entity;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "LOGWARNING", schema = "sahabino")
public class LogWarningsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotNull
    private String warningName;

    @Column
    @NotNull
    private String description;

    @Column
    private String warningType;

    @Column
    @NotNull
    private LocalDateTime logCreatedAt;

    @Column
    @NotNull
    private String system;

}