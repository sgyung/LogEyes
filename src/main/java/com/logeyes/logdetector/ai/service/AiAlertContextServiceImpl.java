package com.logeyes.logdetector.ai.service;

import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.stat.domain.ErrorFingerprintStat5m;
import com.logeyes.logdetector.stat.repository.ErrorFingerprintStat5mRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiAlertContextServiceImpl implements AiAlertContextService {

    private final ErrorFingerprintStat5mRepository fingerprintStatRepository;

    @Override
    public AiAlertAnalysisRequest buildContext(AlertCreatedEvent event) {

        // 최근 15분
        LocalDateTime from = event.getDetectedAt().minusMinutes(15);
        LocalDateTime to = event.getDetectedAt();

        List<ErrorFingerprintStat5m> stats =
                fingerprintStatRepository
                        .findByServiceNameAndEnvironmentAndFingerprintAndTimeBucketBetween(
                                event.getServiceName(),
                                event.getEnvironment(),
                                event.getFingerprint(),
                                from,
                                to
                        );

        Map<String, Integer> recentStats = stats.stream()
                .collect(Collectors.toMap(
                        ErrorFingerprintStat5m::getTimeBucketLabel,
                        ErrorFingerprintStat5m::getErrorCount
                ));

        return AiAlertAnalysisRequest.builder()
                .alertId(event.getAlertId())
                .serviceName(event.getServiceName())
                .environment(event.getEnvironment())
                .fingerprint(event.getFingerprint())
                .severity(event.getSeverity())
                .errorRate(event.getErrorRate())
                .detectedAt(event.getDetectedAt())
                .recentErrorStats(recentStats)
                .build();
    }
}
