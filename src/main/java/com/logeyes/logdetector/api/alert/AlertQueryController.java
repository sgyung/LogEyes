package com.logeyes.logdetector.api.alert;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import com.logeyes.logdetector.alert.dto.AlertResponse;
import com.logeyes.logdetector.alert.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alert API", description = "장애(알림) 조회 및 수동 복구 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertQueryController {

    private final AlertService  alertService;

    @Operation(summary = "알림 단건 조회", description = "특정 alertId에 대한 장애 상세 정보를 조회합니다.")
    @GetMapping("/{alertId}")
    public AlertResponse getAlert(@PathVariable Long alertId) {
        Alert alert = alertService.getAlert(alertId);
        return new AlertResponse(alert);
    }

    @Operation(summary = "최근 알림 조회", description = "최근 발생한 장애 알림을 조회합니다.")
    @GetMapping("/recent")
    public List<AlertResponse> recent() {
        return alertService.getRecentAlerts()
                .stream()
                .map(AlertResponse::new)
                .toList();
    }

    @Operation(summary = "상태별 알림 조회", description = "알림 상태(DETECTED, NOTIFIED, RESOLVED) 기준으로 조회합니다.")
    @GetMapping
    public List<AlertResponse> byStatus(@RequestParam AlertStatus status) {
        return alertService.getAlertByStatus(status)
                .stream()
                .map(AlertResponse::new)
                .toList();
    }


    @Operation(summary = "알림 수동 복구 처리", description = "운영자가 특정 알림을 수동으로 복구(RESOLVED) 처리합니다.")
    @PatchMapping("/{alertId}/resolve")
    public void resolve(@PathVariable Long alertId) {
        alertService.resolvedAlert(alertId);
    }
}
