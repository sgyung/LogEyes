package com.logeyes.logdetector.alert.repository;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // 상태별 알림 조회
    List<Alert> findByStatus(AlertStatus status);

    // 특정 서비스의 알림 조회
    List<Alert> findByServiceName(String serviceName);

    // 감지 시간 범위 조회
    List<Alert> findByDetectedAtBetween(
            LocalDateTime from,
            LocalDateTime to
    );

    // 최근 감지된 알림 Top N
    List<Alert> findTop10ByOrderByDetectedAtDesc();

    // 동일 장애 fingerprint 조회
    List<Alert> findByFingerprint(String fingerprint);

    // ACTIVE 장애 조회
    Optional<Alert> findTopByServiceNameAndEnvironmentAndFingerprintAndStatusNotOrderByCreatedAtDesc(
            String serviceName,
            String environment,
            String fingerprint,
            AlertStatus status
    );
}
