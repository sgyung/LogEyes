package com.logeyes.logdetector.alert.service;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertEventPublisher;
import com.logeyes.logdetector.alert.event.AlertResolvedEvent;
import com.logeyes.logdetector.alert.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertEventPublisher eventPublisher;

    // 알림 생성
    @Override
    public Alert createAlert(Alert alert) {
        log.info("[ALERT-CREATE] service={}, env={}, severity={}", alert.getServiceName(), alert.getEnvironment(), alert.getSeverity());

        Alert savedAlert = alertRepository.save(alert);

        log.debug("[ALERT-CREATE] savedAlert={}", savedAlert.getId());

        eventPublisher.publishAlertCreated(
                new AlertCreatedEvent(
                        savedAlert.getId(),
                        savedAlert.getServiceName(),
                        savedAlert.getEnvironment(),
                        savedAlert.getSeverity(),
                        savedAlert.getErrorRate(),
                        savedAlert.getDetectedAt()
                )
        );

        return savedAlert;
    }

    // 알림 단건 조회
    @Override
    @Transactional(readOnly = true)
    public Alert getAlert(Long id) {

        log.debug("[ALERT-GET] alertId={}", id);

        return  alertRepository.findById(id)
                .orElseThrow(() -> {
                   log.warn("[ALERT-NOT-FOUND] alertId={}", id);

                   return new IllegalArgumentException("알림을 찾을 수 없습니다.");
                });
    }

    // 상태별 알림 조회
    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAlertByStatus(AlertStatus status) {

        log.debug("[ALERT-LIST-BY-STATUS] status={}", status);

        return alertRepository.findByStatus(status);
    }

    // 최근 알림 조회
    @Override
    public List<Alert> getRecentAlerts() {

        log.debug("[ALERT-RECENT-LIST]");

        return alertRepository.findTop10ByOrderByDetectedAtDesc();
    }

    // 알림 해결 처리
    @Override
    public void resolvedAlert(Long id) {

        log.info("[ALERT-RESOLVED-REQUEST] alertId={}", id);

        Alert alert = getAlert(id);
        alert.resolve();

        log.info("[ALERT-RESOLVED] alertId={}", id);
    }

    @Override
    public void recoverAlert(
            String serviceName,
            String environment,
            String fingerprint
    ) {
        alertRepository
                .findTopByServiceNameAndEnvironmentAndFingerprintAndStatusNotOrderByCreatedAtDesc(
                        serviceName,
                        environment,
                        fingerprint,
                        AlertStatus.RESOLVED
                )
                .ifPresent(alert -> {
                    alert.resolve();

                    eventPublisher.publishAlertResolved(
                            new AlertResolvedEvent(
                                    alert.getId(),
                                    alert.getServiceName(),
                                    alert.getEnvironment(),
                                    alert.getFingerprint(),
                                    alert.getSeverity(),
                                    alert.getUpdatedAt()
                            )
                    );

                    log.info("[ALERT-RECOVERY] alertId={}", alert.getId());
                });
    }
}
