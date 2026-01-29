package com.logeyes.logdetector.ai.service;

import com.logeyes.logdetector.ai.dto.AiAlertAnalysisRequest;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;

public interface AiAlertContextService {

    AiAlertAnalysisRequest buildContext(AlertCreatedEvent event);
}
