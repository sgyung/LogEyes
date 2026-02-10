package com.logeyes.logdetector.notification.service;

import com.logeyes.logdetector.notification.domain.AlertNotificationHistory;
import com.logeyes.logdetector.notification.domain.NotificationChannel;

import java.util.List;

public interface AlertNotificationHistoryService {

    //히스토리 이력
    List<AlertNotificationHistory> findByAlertId(Long alertId);

    // 성공 저장
    void saveSuccess(Long alertId, NotificationChannel channel);

    // 실패 저장
    void saveFail(Long alertId, NotificationChannel channel, String errorMessage);
}
