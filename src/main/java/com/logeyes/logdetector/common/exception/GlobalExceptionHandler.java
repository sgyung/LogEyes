package com.logeyes.logdetector.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e
    ) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        code.name(),
                        code.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(
                        "INTERNAL_ERROR",
                        "알 수 없는 서버 오류",
                        LocalDateTime.now()
                ));
    }
}
