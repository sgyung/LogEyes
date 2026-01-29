package com.logeyes.logdetector.test;

import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.ai.dto.AiAnalysisResult;
import com.logeyes.logdetector.ai.service.AiAnalysisService;
import com.logeyes.logdetector.alert.domain.AlertSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/ai")
public class AiTestController {

    private final AiAnalysisService aiAnalysisService;

    @PostMapping
    public AiAnalysisResult test() {

        AiAlertAnalysisRequest request =
                AiAlertAnalysisRequest.builder()
                        .alertId(1L)
                        .serviceName("order-service")
                        .environment("prod")
                        .fingerprint("DB_TIMEOUT")
                        .severity(AlertSeverity.HIGH)
                        .errorRate(0.32)
                        .detectedAt(LocalDateTime.now())
                        .recentErrorStats(Map.of(
                                "10:00", 12,
                                "10:05", 18,
                                "10:10", 32
                        ))
                        .build();

        return aiAnalysisService.analyze(request);
    }

}
