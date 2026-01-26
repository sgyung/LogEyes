package com.logeyes.logdetector.alert.service;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.dto.AlertCreateRequest;
import com.logeyes.logdetector.alert.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;

    // 수동 알림 생성 (운영 / 테스트 목적)
    @Override
    public Alert createManualAlert(AlertCreateRequest request) {
        double errorRate =
                request.getTotalCount() == 0
                        ? 0.0
                        : (double) request.getErrorCount()
                        / request.getTotalCount();

        Alert alert = Alert.createFromStat(
                request.getServiceName(),
                request.getEnvironment(),
                request.getFingerprint(),
                request.getSeverity(),
                request.getWindowSeconds(),
                request.getErrorCount(),
                request.getTotalCount(),
                errorRate
        );

        return alertRepository.save(alert);
    }
}
