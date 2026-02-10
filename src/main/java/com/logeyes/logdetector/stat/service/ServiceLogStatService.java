package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceLogStatService {

    List<ServiceLogStat5m> getStats(LocalDateTime timeBucket);

}
