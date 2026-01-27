package com.logeyes.logdetector.alert.event;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlertResolvedEvent {

    private Long alertId;
    private String serviceName;
    private String environment;
    private String fingerprint;
    private AlertSeverity severity;
    private LocalDateTime resolvedAt;

    public AlertResolvedEvent(
            Long alertId,
            String serviceName,
            String environment,
            String fingerprint,
            AlertSeverity severity,
            LocalDateTime resolvedAt
    ) {
        this.alertId = alertId;
        this.serviceName = serviceName;
        this.environment = environment;
        this.fingerprint = fingerprint;
        this.severity = severity;
        this.resolvedAt = resolvedAt;
    }

}
