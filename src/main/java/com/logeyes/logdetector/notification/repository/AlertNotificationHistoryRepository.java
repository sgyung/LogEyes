package com.logeyes.logdetector.notification.repository;

import com.logeyes.logdetector.notification.domain.AlertNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertNotificationHistoryRepository extends JpaRepository<AlertNotificationHistory, Long> {
    List<AlertNotificationHistory> findByAlertId(Long alertId);
}
