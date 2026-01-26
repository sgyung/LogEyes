package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;

import java.time.LocalDateTime;

public interface StatAlertDetectorService {

    // 5분 통계를 기준으로 장애 감지
    void detect(LocalDateTime timeBucket);

    // 장애 판단 조건
    boolean isAlertCondition(ServiceLogStat5m stat);

    // 심각도 계산
    AlertSeverity determineSeverity(ServiceLogStat5m stat);
}
