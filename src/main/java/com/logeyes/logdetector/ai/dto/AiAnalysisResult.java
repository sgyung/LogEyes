package com.logeyes.logdetector.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AiAnalysisResult {

    private String summary;
    private List<PossibleCause> possibleCauses;
    private List<ChecklistItem> checklist;

}