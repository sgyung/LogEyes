package com.logeyes.logdetector.api.ai;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAlertAnalysisResponse;
import com.logeyes.logdetector.ai.service.AiAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI Analysis API", description = "알림 AI 분석 결과 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts/{alertId}/ai-analysis")
public class AiAlertAnalysisController {
    private final AiAnalysisService analysisService;

    @Operation(
            summary = "AI 분석 결과 조회",
            description = "특정 알림(alertId)에 대한 AI 분석 결과를 조회합니다."
    )
    @GetMapping
    public AiAlertAnalysisResponse get(@PathVariable Long alertId) {
        AlertAiAnalysis analysis = analysisService.getByAlertId(alertId);

        return new AiAlertAnalysisResponse(analysis);
    }

}
