package com.logeyes.logdetector.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logeyes.logdetector.ai.client.GeminiRestClient;
import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.ai.dto.AiAnalysisResult;
import com.logeyes.logdetector.ai.prompt.AiPromptBuilder;
import com.logeyes.logdetector.ai.repository.AlertAiAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiAnalysisServiceImpl implements AiAnalysisService{

    private final GeminiRestClient geminiClient;
    private final AiPromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;
    private final AlertAiAnalysisRepository alertAiAnalysisRepository;

    @Override
    public AiAnalysisResult analyze(AiAlertAnalysisRequest request) {

        try {
            String prompt = promptBuilder.build(request);
            String response = geminiClient.request(prompt);

            JsonNode root = objectMapper.readTree(response);

            String text =
                    root.path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

            text = text
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(text, AiAnalysisResult.class);

        } catch (Exception e) {

            log.error("[GEMINI] 분석 실패 - fallback", e);

            return new AiAnalysisResult(
                    "Gemini 분석에 실패했습니다.",
                    List.of(),
                    List.of()
            );
        }
    }

    @Override
    public AlertAiAnalysis getByAlertId(Long alertId) {
        return alertAiAnalysisRepository.findByAlertId(alertId)
                .orElseThrow(() -> new IllegalArgumentException("AI 분석 결과가 없습니다."));
    }

}
