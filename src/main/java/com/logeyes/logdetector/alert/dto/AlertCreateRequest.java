package com.logeyes.logdetector.alert.dto;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AlertCreateRequest {

    @NotBlank
    private String serviceName;

    @NotBlank
    private String environment;

    @NotBlank
    private String fingerprint;

    @NotNull
    private AlertSeverity  severity;

    private int windowSeconds;
    private int errorCount;
    private int totalCount;

    public Alert toEntity() {
        return Alert.create(
                serviceName,
                environment,
                fingerprint,
                severity,
                windowSeconds,
                errorCount,
                totalCount
        );
    }

}
