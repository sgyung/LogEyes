package com.logeyes.logdetector.stat.dto;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ServiceLogStatResponse {

    private String serviceName;
    private String environment;
    private int totalCount;
    private int errorCount;
    private int warnCount;
    private BigDecimal errorRate;
    private LocalDateTime timeBucket;

    public ServiceLogStatResponse(ServiceLogStat5m serviceLogStat5m){
        this.serviceName = serviceLogStat5m.getServiceName();
        this.environment = serviceLogStat5m.getEnvironment();
        this.totalCount = serviceLogStat5m.getTotalCount();
        this.errorCount = serviceLogStat5m.getErrorCount();
        this.warnCount = serviceLogStat5m.getWarnCount();
        this.errorRate = serviceLogStat5m.getErrorRate();
        this.timeBucket = serviceLogStat5m.getTimeBucket();
    }
}
