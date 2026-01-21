package com.logeyes.logdetector.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private String code;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String  code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }
}
