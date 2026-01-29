package com.logeyes.logdetector.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PossibleCause {

    private String cause;
    private double confidence;

}
