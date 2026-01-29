package com.logeyes.logdetector.ai.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAnalysisResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertAiAnalysisMapper {

    private final ObjectMapper objectMapper;

    public void applySuccessResult(
            AlertAiAnalysis entity,
            AiAnalysisResult result
    ) {
        try {
            entity.success(
                    result.getSummary(),
                    objectMapper.writeValueAsString(result.getPossibleCauses()),
                    objectMapper.writeValueAsString(result.getChecklist())
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Gemini 결과 JSON 직렬화 실패", e);
        }
    }
}
