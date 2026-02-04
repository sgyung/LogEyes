package com.logeyes.logdetector.stat.rawlog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RawLogEvent {

    private String serviceName;
    private String environment;
    private String level;
    private String message;
    private String fingerprint;
    private LocalDateTime occurredAt;

}
