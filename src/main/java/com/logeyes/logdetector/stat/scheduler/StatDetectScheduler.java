package com.logeyes.logdetector.stat.scheduler;

import com.logeyes.logdetector.stat.service.StatAggregationService;
import com.logeyes.logdetector.stat.service.StatAlertDetectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatDetectScheduler {

    private final StatAggregationService statAggregationService;
    private final StatAlertDetectorService statAlertDetectorService;

    // 매 5분마다 실행
    @Scheduled(cron = "0 */1 * * * *")
    public void run() {
        // timeBucket을 "현재 시각"이 아니라 "5분 단위 버킷"으로 맞추는 게 핵심
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bucket = to5MinuteBucket(now);

        // 샘플 대상 (나중에 서비스 목록을 DB/설정에서 가져오면 됨)
        String serviceName = "order-api";
        String environment = "prod";

        log.info("[SCHEDULER] start bucket={}", bucket);

        statAggregationService.createAndSaveStat(serviceName, environment, bucket);
        statAlertDetectorService.detect(bucket);

        log.info("[SCHEDULER] end bucket={}", bucket);
    }

    private LocalDateTime to5MinuteBucket(LocalDateTime time) {
        int minute = time.getMinute();
        int bucketMinute = (minute / 5) * 5;

        return time.withMinute(bucketMinute)
                .withSecond(0)
                .withNano(0);
    }
}
