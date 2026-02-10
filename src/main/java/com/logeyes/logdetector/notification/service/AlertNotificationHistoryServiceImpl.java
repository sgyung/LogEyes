package com.logeyes.logdetector.notification.service;

import com.logeyes.logdetector.notification.domain.AlertNotificationHistory;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.repository.AlertNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlertNotificationHistoryServiceImpl implements AlertNotificationHistoryService {

    private final AlertNotificationHistoryRepository repository;

    @Override
    public List<AlertNotificationHistory> findByAlertId(Long alertId) {

        return repository.findByAlertId(alertId);
    }

    @Override
    public void saveSuccess(Long alertId, NotificationChannel channel) {
        repository.save(AlertNotificationHistory.success(alertId, channel));

        log.info("[ALERT-NOTIFY-HISTORY] SUCCESS alertId={}, channel={}", alertId, channel);
    }

    @Override
    public void saveFail(Long alertId, NotificationChannel channel, String errorMessage) {
        repository.save(
                AlertNotificationHistory.fail(alertId, channel, errorMessage)
        );

        log.warn(
                "[ALERT-NOTIFY-HISTORY] FAIL alertId={}, channel={}, error={}",
                alertId,
                channel,
                errorMessage
        );
    }
}
