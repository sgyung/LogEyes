package com.logeyes.logdetector.api.notification;

import com.logeyes.logdetector.notification.dto.NotificationHistoryResponse;
import com.logeyes.logdetector.notification.service.AlertNotificationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts/{alertId}/notifications")
public class AlertNotificationHistoryController {

    private final AlertNotificationHistoryService historyService;

    @GetMapping
    public List<NotificationHistoryResponse> getHistories(@PathVariable Long alertId) {
        return historyService.findByAlertId(alertId)
                .stream()
                .map(NotificationHistoryResponse::new)
                .toList();
    }

}
