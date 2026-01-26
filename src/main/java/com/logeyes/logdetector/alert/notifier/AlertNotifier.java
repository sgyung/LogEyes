package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.result.NotificationResult;

public interface AlertNotifier {

    // 알림 처리 가능 구분
    boolean support(AlertSeverity severity);

    // 알림 처리
    NotificationResult notify(AlertCreatedEvent event);

    // 알림의 정체성
    NotificationChannel channel();
}
