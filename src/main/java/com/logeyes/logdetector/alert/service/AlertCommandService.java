package com.logeyes.logdetector.alert.service;


import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.dto.AlertCreateRequest;

public interface AlertCommandService {

    // 수동 알림 생성
    Alert createManualAlert(AlertCreateRequest request);
}
