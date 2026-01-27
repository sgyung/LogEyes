package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import com.logeyes.logdetector.stat.repository.ServiceLogStat5mRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatAggregationServiceImpl implements StatAggregationService {

    private final ServiceLogStat5mRepository repository;
    private final ServiceLogStatAggregator aggregator;

    @Override
    public ServiceLogStat5m createAndSaveStat(String serviceName, String environment, LocalDateTime timeBucket) {

        ServiceLogStat5m stat = aggregator.aggregate(serviceName, environment, timeBucket);
        ServiceLogStat5m saved = repository.save(stat);

        log.info("[STAT-SAVED] id={}, bucket={}, service={}, env={}",
                saved.getId(), saved.getTimeBucket(), saved.getServiceName(), saved.getEnvironment());

        return saved;
    }
}
