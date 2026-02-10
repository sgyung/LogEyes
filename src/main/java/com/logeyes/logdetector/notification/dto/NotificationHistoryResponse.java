package com.logeyes.logdetector.notification.dto;

import com.logeyes.logdetector.notification.domain.AlertNotificationHistory;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.domain.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationHistoryResponse {

    private NotificationChannel channel;
    private NotificationStatus success;
    private String errorMessage;
    private LocalDateTime sentAt;

    public NotificationHistoryResponse (AlertNotificationHistory history) {
        this.channel = history.getChannel();
        this.success = history.getStatus();
        this.errorMessage = history.getErrorMessage();
        this.sentAt = history.getSentAt();
    }
}
