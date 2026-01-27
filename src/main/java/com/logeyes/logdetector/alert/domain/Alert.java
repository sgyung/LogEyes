package com.logeyes.logdetector.alert.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "alter",
        indexes = {
                @Index(name = "idx_alter_service_time", columnList = "service_name, detected_at"),
                @Index(name = "idx_alter_fingerprint", columnList = "fingerprint")
        }
)
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alter_id")
    private Long id;

    // 서비스명 (ex: auth-api, order-api)
    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;

    // 실행 환경 (prod, dev)
    @Column(nullable = false, length = 10)
    private String environment;

    // 동일 장애 식별용 fingerprint
    @Column(nullable = false, length = 100)
    private String fingerprint;

    // 장애 심각도
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AlertSeverity severity;

    // 장애 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlertStatus status;

    // 장애 감지 시각
    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;

    // 분석 윈도우 (초 단위)
    @Column(name = "window_seconds", nullable = false)
    private int windowSeconds;

    // 에러 로그 수
    @Column(name = "error_count", nullable = false)
    private int errorCount;

    // 전체 로그 수
    @Column(name = "total_count", nullable = false)
    private int totalCount;

    // 에러 비율
    @Column(name = "error_rate", nullable = false)
    private double errorRate;

    // 생성 시각
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 상태 변경 시각
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* ===========================
       생성 메서드
       =========================== */

    public static Alert createFromStat(
            String serviceName,
            String environment,
            String fingerprint,
            AlertSeverity severity,
            int windowSeconds,
            int errorCount,
            int totalCount,
            double errorRate
    ) {
        Alert alert = new Alert();
        alert.serviceName = serviceName;
        alert.environment = environment;
        alert.fingerprint = fingerprint;
        alert.severity = severity;
        alert.status = AlertStatus.DETECTED;
        alert.detectedAt = LocalDateTime.now();
        alert.windowSeconds = windowSeconds;
        alert.errorCount = errorCount;
        alert.totalCount = totalCount;
        alert.errorRate = errorRate;
        alert.createdAt = LocalDateTime.now();
        return alert;
    }

    // =============== 도메인 로직 ========================

    public void markNotified() {
        this.status = AlertStatus.NOTIFIED;
        this.updatedAt = LocalDateTime.now();
    }

    public void resolve() {
        if (this.status == AlertStatus.RESOLVED) {
            return;
        }

        this.status = AlertStatus.RESOLVED;
        this.updatedAt = LocalDateTime.now();
    }
}
