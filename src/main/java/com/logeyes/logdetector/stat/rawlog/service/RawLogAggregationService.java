package com.logeyes.logdetector.stat.rawlog.service;

import com.logeyes.logdetector.stat.rawlog.dto.RawLogEvent;

public interface RawLogAggregationService {

    void aggregate(RawLogEvent log);

}
