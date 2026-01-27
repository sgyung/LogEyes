package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;

import java.time.LocalDateTime;

public interface ServiceLogStatAggregator {

    ServiceLogStat5m aggregate(String serviceName, String environment, LocalDateTime timeBucket);
}
