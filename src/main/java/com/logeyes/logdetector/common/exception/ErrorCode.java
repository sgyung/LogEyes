package com.logeyes.logdetector.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ALERT_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
