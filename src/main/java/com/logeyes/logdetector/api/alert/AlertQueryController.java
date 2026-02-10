package com.logeyes.logdetector.api.alert;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import com.logeyes.logdetector.alert.dto.AlertResponse;
import com.logeyes.logdetector.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertQueryController {

    private final AlertService  alertService;

    @GetMapping("/{alertId}")
    public AlertResponse getAlert(@PathVariable Long alertId) {
        Alert alert = alertService.getAlert(alertId);
        return new AlertResponse(alert);
    }

    @GetMapping("/recent")
    public List<AlertResponse> recent() {
        return alertService.getRecentAlerts()
                .stream()
                .map(AlertResponse::new)
                .toList();
    }

    @GetMapping
    public List<AlertResponse> byStatus(@RequestParam AlertStatus status) {
        return alertService.getAlertByStatus(status)
                .stream()
                .map(AlertResponse::new)
                .toList();
    }

    /**
     * 운영자가 수동으로 복구 처리
     */
    @PatchMapping("/{alertId}/resolve")
    public void resolve(@PathVariable Long alertId) {
        alertService.resolvedAlert(alertId);
    }
}
