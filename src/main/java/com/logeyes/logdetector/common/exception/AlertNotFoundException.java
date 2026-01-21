package com.logeyes.logdetector.common.exception;

public class AlertNotFoundException extends BusinessException{

    public AlertNotFoundException() {
        super(ErrorCode.ALERT_NOT_FOUND);
    }
}
