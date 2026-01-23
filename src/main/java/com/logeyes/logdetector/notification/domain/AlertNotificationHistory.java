package com.logeyes.logdetector.notification.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alert_notification_history")
public class AlertNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alert_id", nullable = false)
    private Long alertId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    public static AlertNotificationHistory success(
            Long alertId,
            NotificationChannel channel
    ) {
        AlertNotificationHistory history = new AlertNotificationHistory();
        history.alertId = alertId;
        history.channel = channel;
        history.status = NotificationStatus.SUCCESS;
        history.sentAt = LocalDateTime.now();
        return history;
    }

    public static AlertNotificationHistory fail(
            Long alertId,
            NotificationChannel channel,
            String errorMessage
    ) {
        AlertNotificationHistory history = new AlertNotificationHistory();
        history.alertId = alertId;
        history.channel = channel;
        history.status = NotificationStatus.FAIL;
        history.errorMessage = errorMessage;
        history.sentAt = LocalDateTime.now();
        return history;
    }
}
