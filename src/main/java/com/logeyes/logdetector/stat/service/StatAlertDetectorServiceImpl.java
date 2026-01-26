package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.service.AlertService;
import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import com.logeyes.logdetector.stat.repository.ServiceLogStat5mRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatAlertDetectorServiceImpl implements StatAlertDetectorService {

    private final ServiceLogStat5mRepository serviceLogStat5mRepository;
    private final AlertService alertService;

    // 5분 통계를 기준으로 장애 감지
    @Override
    public void detect(LocalDateTime timeBucket) {
        List<ServiceLogStat5m> stats = serviceLogStat5mRepository.findByTimeBucket(timeBucket);

        log.info("[STAT-DETECT] timeBucket={}, statCount={}", timeBucket, stats.size());

        for(ServiceLogStat5m stat : stats){

            if(isAlertCondition(stat)){
                Alert alert = Alert.createFromStat(
                        stat.getServiceName(),
                        stat.getEnvironment(),
                        "SERVICE_ERROR_RATE",
                        determineSeverity(stat),
                        300, // 5분(=300초) 윈도우
                        stat.getErrorCount(),
                        stat.getTotalCount(),
                        stat.getErrorRate()
                );

                alertService.createAlert(alert);

                log.warn("[ALERT-DETECTED] service={}, env={}, errorRate={}",
                        stat.getServiceName(), stat.getEnvironment(), stat.getErrorRate());
            }


        }
    }

    // 장애 판단 조건
    @Override
    public boolean isAlertCondition(ServiceLogStat5m stat) {
        return stat.getErrorRate() >= 0.05;
    }

    // 심각도 계산
    @Override
    public AlertSeverity determineSeverity(ServiceLogStat5m stat) {
        double rate = stat.getErrorRate();

        if (rate >= 0.20) return AlertSeverity.CRITICAL;
        if (rate >= 0.10) return AlertSeverity.HIGH;
        if (rate >= 0.05) return AlertSeverity.MEDIUM;

        return AlertSeverity.LOW;
    }
}
