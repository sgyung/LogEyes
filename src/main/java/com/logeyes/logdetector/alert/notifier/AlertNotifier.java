package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;

public interface AlertNotifier {

    // 알림 처리 가능 구분
    boolean support(AlertSeverity severity);

    // 알림 처리
    void notify(AlertCreatedEvent event);
}
