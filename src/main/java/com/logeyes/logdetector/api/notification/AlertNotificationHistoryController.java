package com.logeyes.logdetector.api.notification;

import com.logeyes.logdetector.notification.dto.NotificationHistoryResponse;
import com.logeyes.logdetector.notification.service.AlertNotificationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Notification API", description = "알림 전송 이력 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts/{alertId}/notifications")
public class AlertNotificationHistoryController {

    private final AlertNotificationHistoryService historyService;

    @Operation(
            summary = "알림 전송 이력 조회",
            description = "특정 알림(alertId)에 대해 Slack/Email 등으로 전송된 이력 목록을 조회합니다."
    )
    @GetMapping
    public List<NotificationHistoryResponse> getHistories(@PathVariable Long alertId) {
        return historyService.findByAlertId(alertId)
                .stream()
                .map(NotificationHistoryResponse::new)
                .toList();
    }

}
