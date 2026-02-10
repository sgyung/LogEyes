package com.logeyes.logdetector.ai.service;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.ai.dto.AiAnalysisResult;

public interface AiAnalysisService {

    AiAnalysisResult analyze(AiAlertAnalysisRequest request);

    AlertAiAnalysis getByAlertId(Long alertId);

}
