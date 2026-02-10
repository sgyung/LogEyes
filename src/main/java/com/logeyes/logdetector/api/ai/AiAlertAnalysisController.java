package com.logeyes.logdetector.api.ai;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAlertAnalysisResponse;
import com.logeyes.logdetector.ai.service.AiAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts/{alertId}/ai-analysis")
public class AiAlertAnalysisController {
    private final AiAnalysisService analysisService;

    @GetMapping
    public AiAlertAnalysisResponse get(@PathVariable Long alertId) {
        AlertAiAnalysis analysis = analysisService.getByAlertId(alertId);

        return new AiAlertAnalysisResponse(analysis);
    }

}
