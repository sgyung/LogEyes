package com.logeyes.logdetector.ai.dto;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AiAlertAnalysisResponse {

    private Long alertId;
    private String model;
    private String status;
    private String summary;
    private String possibleCauses; // JSON String 그대로 노출
    private String checklist;      // JSON String 그대로 노출
    private String errorMessage;
    private LocalDateTime createdAt;

    public AiAlertAnalysisResponse(AlertAiAnalysis alertAiAnalysis) {
        this.alertId = alertAiAnalysis.getAlertId();
        this.model = alertAiAnalysis.getModel();
        this.status =  alertAiAnalysis.getStatus().toString();
        this.summary = alertAiAnalysis.getSummary();
        this.possibleCauses = alertAiAnalysis.getPossibleCauses();
        this.checklist = alertAiAnalysis.getChecklist();
        this.errorMessage = alertAiAnalysis.getErrorMessage();
        this.createdAt = alertAiAnalysis.getCreatedAt();
    }
}
