package com.logeyes.logdetector.stat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "service_log_stat_5m")
public class ServiceLogStat5m {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_bucket", nullable = false)
    private LocalDateTime timeBucket;

    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "environment", nullable = false, length = 10)
    private String environment;

    @Column(name = "total_count", nullable = false)
    private int totalCount;

    @Column(name = "error_count", nullable = false)
    private int errorCount;

    @Column(name = "warn_count", nullable = false)
    private int warnCount;

    @Column(name = "error_rate", nullable = false, precision = 5, scale = 4)
    private double errorRate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* ==========================
       생성 로직
       ========================== */

    public static ServiceLogStat5m create(
            LocalDateTime timeBucket,
            String serviceName,
            String environment,
            int totalCount,
            int errorCount,
            int warnCount
    ) {
        ServiceLogStat5m stat = new ServiceLogStat5m();
        stat.timeBucket = timeBucket;
        stat.serviceName = serviceName;
        stat.environment = environment;
        stat.totalCount = totalCount;
        stat.errorCount = errorCount;
        stat.warnCount = warnCount;
        stat.errorRate = stat.calculateErrorRate();
        stat.createdAt = LocalDateTime.now();
        return stat;
    }

    /* ==========================
       계산 책임
       ========================== */

    private double calculateErrorRate() {
        if (totalCount == 0) {
            return 0.0;
        }
        return (double) errorCount / totalCount;
    }
}
