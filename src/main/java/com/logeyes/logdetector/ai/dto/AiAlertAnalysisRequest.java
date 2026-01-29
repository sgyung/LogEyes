package com.logeyes.logdetector.ai.dto;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class AiAlertAnalysisRequest {

    private final Long alertId;

    private final String serviceName;
    private final String environment;
    private final String fingerprint;

    private final AlertSeverity severity;
    private final double errorRate;

    private final LocalDateTime detectedAt;

    /**
     * 최근 에러 통계
     * 예)
     * DB_TIMEOUT → 32
     * CONNECTION_REFUSED → 12
     */
    private final Map<String, Integer> recentErrorStats;

}
