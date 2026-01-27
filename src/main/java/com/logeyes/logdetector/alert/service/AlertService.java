package com.logeyes.logdetector.alert.service;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;

import java.util.List;
import java.util.Optional;

public interface AlertService {

    // 알림 생성
    Alert createAlert(Alert alert);

    // 알림 단일 조회
    Alert getAlert(Long id);

    // 상태별 알림 조회
    List<Alert> getAlertByStatus(AlertStatus status);

    // 최근 알림 조화
    List<Alert> getRecentAlerts();

    // 알림 해결 처리
    void resolvedAlert(Long id);

    void recoverAlert(
            String serviceName,
            String environment,
            String fingerprint
    );
}
