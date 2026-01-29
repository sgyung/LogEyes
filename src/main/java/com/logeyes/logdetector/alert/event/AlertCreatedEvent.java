package com.logeyes.logdetector.alert.event;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlertCreatedEvent {

    private Long alertId;
    private String serviceName;
    private String environment;
    private String fingerprint;
    private AlertSeverity severity;
    private double errorRate;
    private LocalDateTime detectedAt;

    public AlertCreatedEvent(
            Long alertId,
            String serviceName,
            String environment,
            String fingerprint,
            AlertSeverity severity,
            double errorRate,
            LocalDateTime detectedAt
    ) {
        this.alertId = alertId;
        this.serviceName = serviceName;
        this.environment = environment;
        this.fingerprint = fingerprint;
        this.severity = severity;
        this.errorRate = errorRate;
        this.detectedAt = detectedAt;
    }

}
