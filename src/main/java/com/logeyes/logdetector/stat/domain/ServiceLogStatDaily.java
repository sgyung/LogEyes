package com.logeyes.logdetector.stat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "service_log_stat_daily")
public class ServiceLogStatDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "environment", nullable = false, length = 10)
    private String environment;

    @Column(name = "total_logs", nullable = false)
    private int totalLogs;

    @Column(name = "total_errors", nullable = false)
    private int totalErrors;

    @Column(name = "avg_error_rate", nullable = false, precision = 5, scale = 4)
    private double avgErrorRate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
