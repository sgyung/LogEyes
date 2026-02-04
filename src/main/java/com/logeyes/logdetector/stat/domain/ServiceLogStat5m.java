package com.logeyes.logdetector.stat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal errorRate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

  /* ==========================
       생성 로직
       ========================== */

    public static ServiceLogStat5m create(
            LocalDateTime timeBucket,
            String serviceName,
            String environment
    ) {
        ServiceLogStat5m stat = new ServiceLogStat5m();
        stat.timeBucket = timeBucket;
        stat.serviceName = serviceName;
        stat.environment = environment;
        stat.totalCount = 0;
        stat.errorCount = 0;
        stat.warnCount = 0;
        stat.errorRate = BigDecimal.ZERO;
        return stat;
    }

    /* ==========================
       도메인 행위
       ========================== */

    public void increaseTotal() {
        this.totalCount++;
        recalculateErrorRate();
    }

    public void increaseError() {
        this.errorCount++;
        recalculateErrorRate();
    }

    public void increaseWarn() {
        this.warnCount++;
    }

    private void recalculateErrorRate() {
        if (totalCount == 0) {
            this.errorRate = BigDecimal.ZERO;
            return;
        }

        this.errorRate = BigDecimal.valueOf(errorCount)
                .divide(
                        BigDecimal.valueOf(totalCount),
                        4,
                        RoundingMode.HALF_UP
                );
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
