package com.logeyes.logdetector.test;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import com.logeyes.logdetector.stat.repository.ServiceLogStat5mRepository;
import com.logeyes.logdetector.stat.service.StatAlertDetectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test/stat")
@RequiredArgsConstructor
public class StatScenarioTestController {

    private final ServiceLogStat5mRepository statRepository;
    private final StatAlertDetectorService detectorService;

    /**
     * 시나리오 테스트용 API
     *
     * total, error 값으로
     * 장애 / 정상 / 복구 시나리오 테스트
     *
     * 예)
     *  - total=100, error=20 → 장애
     *  - total=100, error=1  → 복구
     */
    @PostMapping
    public String createStatAndDetect(
            @RequestParam int total,
            @RequestParam int error
    ) {

        LocalDateTime bucket = LocalDateTime.now()
                .withSecond(0)
                .withNano(0);

        ServiceLogStat5m stat = ServiceLogStat5m.create(
                bucket,
                "order-api",
                "prod"
        );

        statRepository.save(stat);

        detectorService.detect(bucket);

        return "STAT CREATED & DETECTED : total=" + total + ", error=" + error;
    }
}
