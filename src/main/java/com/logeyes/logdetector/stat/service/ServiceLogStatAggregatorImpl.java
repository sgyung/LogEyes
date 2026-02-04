package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ServiceLogStatAggregatorImpl implements ServiceLogStatAggregator {

    @Override
    public ServiceLogStat5m aggregate(String serviceName, String environment, LocalDateTime timeBucket) {

        int totalCount = 200;
        int errorCount = 15;
        int warnCount = 5;

        ServiceLogStat5m stat = ServiceLogStat5m.create(
                timeBucket,
                serviceName,
                environment
        );

        log.info("[STAT-AGGREGATE] bucket={}, service={}, env={}, total={}, error={}, warn={}, errorRate={}",
                timeBucket, serviceName, environment, totalCount, errorCount, warnCount, stat.getErrorRate());

        return stat;
    }
}
