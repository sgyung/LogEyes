package com.logeyes.logdetector.test;

import com.logeyes.logdetector.stat.service.StatAggregationService;
import com.logeyes.logdetector.stat.service.StatAlertDetectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "TEST - LogEyes Flow Test")
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class LogEyesTestController {

    private final StatAggregationService statAggregationService;
    private final StatAlertDetectorService statAlertDetectorService;

    @Operation(summary = "stat 생성 테스트")
    @GetMapping("/stat")
    public String createStat() {
        statAggregationService.createAndSaveStat(
                "order-api",
                "prod",
                nowBucket()
        );
        return "stat created";
    }

    @Operation(summary = "detect 실행 테스트")
    @GetMapping("/detect")
    public String detect() {
        statAlertDetectorService.detect(nowBucket());
        return "detect executed";
    }

    @Operation(summary = "stat + detect 전체 흐름 테스트")
    @GetMapping("/run")
    public String run() {
        LocalDateTime bucket = nowBucket();

        statAggregationService.createAndSaveStat(
                "order-api",
                "prod",
                bucket
        );

        statAlertDetectorService.detect(bucket);

        return "run completed";
    }

    private LocalDateTime nowBucket() {
        LocalDateTime now = LocalDateTime.now();
        int minute = (now.getMinute() / 5) * 5;
        return now.withMinute(minute).withSecond(0).withNano(0);
    }

}
