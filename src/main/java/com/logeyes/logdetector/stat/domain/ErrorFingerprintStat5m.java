package com.logeyes.logdetector.stat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "error_fingerprint_stat_5m")
public class ErrorFingerprintStat5m {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_bucket", nullable = false)
    private LocalDateTime timeBucket;

    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "environment", nullable = false, length = 10)
    private String environment;

    @Column(name = "fingerprint", nullable = false, length = 100)
    private String fingerprint;

    @Column(name = "error_count", nullable = false)
    private int errorCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
