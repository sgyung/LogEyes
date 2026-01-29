package com.logeyes.logdetector.ai.prompt;

import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import org.springframework.stereotype.Component;

@Component
public class AiPromptBuilder {

    public String build(AiAlertAnalysisRequest request) {

        return """
        당신은 대규모 서비스 운영을 담당하는 SRE 엔지니어입니다.

    ⚠️ 반드시 모든 응답은 한국어로 작성하세요.
    ⚠️ JSON 형식만 반환하세요. 설명 문장은 절대 포함하지 마세요.
    ⚠️ 코드 블록(```)을 사용하지 마세요.

    아래 장애 정보를 분석하고,
    원인 분석 및 운영 대응 가이드를 생성하세요.

    출력 형식(JSON):

    {
      "summary": "장애 요약",
      "possibleCauses": [
        { "cause": "원인", "confidence": 0.0 }
      ],
      "checklist": [
        { "step": 1, "action": "대응 조치" }
      ]
    }

    [장애 정보]
    - 서비스명: %s
    - 환경: %s
    - 장애 심각도: %s
    - 에러율: %.4f
    - 탐지 시각: %s
    - 에러 fingerprint: %s
    - 최근 에러 통계: %s
    """.formatted(
                request.getServiceName(),
                request.getEnvironment(),
                request.getSeverity(),
                request.getErrorRate(),
                request.getDetectedAt(),
                request.getFingerprint(),
                request.getRecentErrorStats()
        );
    }
}
