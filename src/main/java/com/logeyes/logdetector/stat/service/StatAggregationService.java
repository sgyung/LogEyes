package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;

import java.time.LocalDateTime;

public interface StatAggregationService {

    ServiceLogStat5m createAndSaveStat(String serviceName, String environment, LocalDateTime timeBucket);
}
