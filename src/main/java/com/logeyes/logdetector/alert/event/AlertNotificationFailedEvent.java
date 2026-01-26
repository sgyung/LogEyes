package com.logeyes.logdetector.alert.event;

import com.logeyes.logdetector.notification.domain.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlertNotificationFailedEvent {

    private Long alertId;
    private NotificationChannel channel;
    private String errorMessage;
    private LocalDateTime failedAt;
}
