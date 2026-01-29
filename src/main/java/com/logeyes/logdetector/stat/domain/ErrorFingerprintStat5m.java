package com.logeyes.logdetector.stat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "error_fingerprint_stat_5m",
        indexes = {
                @Index(
                        name = "idx_stat_fp_service_env_time",
                        columnList = "service_name, environment, fingerprint, time_bucket"
                )
        }
)
public class ErrorFingerprintStat5m {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 5분 단위 기준 시각 (ex: 10:00, 10:05, 10:10)
     */
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

    /* ===============================
       JPA LifeCycle
       =============================== */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ===============================
       Factory Method
       =============================== */

    public static ErrorFingerprintStat5m create(
            LocalDateTime timeBucket,
            String serviceName,
            String environment,
            String fingerprint,
            int errorCount
    ) {
        ErrorFingerprintStat5m stat = new ErrorFingerprintStat5m();
        stat.timeBucket = timeBucket;
        stat.serviceName = serviceName;
        stat.environment = environment;
        stat.fingerprint = fingerprint;
        stat.errorCount = errorCount;
        return stat;
    }

    /* ===============================
       AI 설명용 보조 메서드
       =============================== */

    /**
     * AI 문장용 시간 표현
     * 예: "10:05"
     */
    public String getTimeBucketLabel() {
        return timeBucket.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
