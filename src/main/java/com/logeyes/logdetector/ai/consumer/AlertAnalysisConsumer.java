package com.logeyes.logdetector.ai.consumer;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.ai.dto.AiAnalysisResult;
import com.logeyes.logdetector.ai.mapper.AlertAiAnalysisMapper;
import com.logeyes.logdetector.ai.repository.AlertAiAnalysisRepository;
import com.logeyes.logdetector.ai.service.AiAlertContextService;
import com.logeyes.logdetector.ai.service.AiAnalysisService;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertAnalysisConsumer {

    private final AiAlertContextService contextService;
    private final AiAnalysisService aiAnalysisService;
    private final AlertAiAnalysisMapper mapper;
    private final AlertAiAnalysisRepository analysisRepository;

    @KafkaListener(
            topics = "alert.created",
            groupId = "alert-ai-analyzer",
            containerFactory = "alertCreatedKafkaListenerContainerFactory"
    )
    public void consume(AlertCreatedEvent event) {

        log.info("[AI] Gemini 분석 시작 - alertId={}", event.getAlertId());

        AiAlertAnalysisRequest request =
                contextService.buildContext(event);

        AlertAiAnalysis analysis =
                AlertAiAnalysis.pending(
                        event.getAlertId(),
                        "gemini-2.5-flash"
                );

        analysisRepository.save(analysis);

        try {
            // 3. AI 호출
            AiAnalysisResult result =
                    aiAnalysisService.analyze(request);

            // 4. 성공 반영
            mapper.applySuccessResult(analysis, result);

        } catch (Exception e) {

            log.error("[AI] Gemini 분석 실패 - alertId={}", event.getAlertId(), e);

            analysis.fail(e.getMessage());
        }

        // 5. 최종 저장
        analysisRepository.save(analysis);

        log.info("[AI] Gemini 분석 종료 - alertId={}", event.getAlertId());
    }
}
