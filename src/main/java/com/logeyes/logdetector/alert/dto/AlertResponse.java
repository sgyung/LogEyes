package com.logeyes.logdetector.alert.dto;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertResponse {

    private Long id;
    private String serviceName;
    private String environment;
    private AlertSeverity severity;
    private AlertStatus status;
    private double errorRate;
    private LocalDateTime detectedAt;

    public AlertResponse(Alert alert) {
        this.id = alert.getId();
        this.serviceName = alert.getServiceName();
        this.environment = alert.getEnvironment();
        this.severity = alert.getSeverity();
        this.status = alert.getStatus();
        this.errorRate = alert.getErrorRate();
        this.detectedAt = alert.getDetectedAt();
    }
}
