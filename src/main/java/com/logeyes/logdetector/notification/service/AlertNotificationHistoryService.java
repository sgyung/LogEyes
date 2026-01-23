package com.logeyes.logdetector.notification.service;

import com.logeyes.logdetector.notification.domain.NotificationChannel;

public interface AlertNotificationHistoryService {

    // 성공 저장
    void saveSuccess(Long alertId, NotificationChannel channel);

    // 실패 저장
    void saveFail(Long alertId, NotificationChannel channel, String errorMessage);
}
