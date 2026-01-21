package com.logeyes.logdetector.alert.controller;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import com.logeyes.logdetector.alert.dto.AlertCreateRequest;
import com.logeyes.logdetector.alert.dto.AlertResponse;
import com.logeyes.logdetector.alert.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Operation(summary = "알림 생성")
    @PostMapping()
    public AlertResponse create(@RequestBody @Valid AlertCreateRequest request) {
        Alert alert = alertService.createAlert(request.toEntity());

        return new AlertResponse(alert);
    }

    @Operation(summary = "알림 단건 조회")
    @GetMapping("/{alertId}")
    public AlertResponse getAlert(@PathVariable("alertId") Long alertId) {
        Alert alert = alertService.getAlert(alertId);

        return new AlertResponse(alert);
    }

    @Operation(summary = "상태별 알림 조회")
    @GetMapping
    public List<AlertResponse> getByStatus(
            @RequestParam AlertStatus status
    ) {

        return alertService.getAlertByStatus(status)
                .stream()
                .map(AlertResponse::new)
                .toList();
    }

    @Operation(summary = "알림 해결 처리")
    @PatchMapping("/{alertId}/resolve")
    public void resolve(@PathVariable Long alertId) {
        alertService.resolvedAlert(alertId);
    }
}
