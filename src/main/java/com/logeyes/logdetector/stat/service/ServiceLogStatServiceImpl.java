package com.logeyes.logdetector.stat.service;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import com.logeyes.logdetector.stat.repository.ServiceLogStat5mRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceLogStatServiceImpl implements  ServiceLogStatService {

    private final ServiceLogStat5mRepository serviceLogStat5mRepository;

    @Override
    public List<ServiceLogStat5m> getStats(LocalDateTime timeBucket) {

        LocalDateTime bucket = timeBucket != null
                ? timeBucket
                : findLatestTimeBucket();

        return serviceLogStat5mRepository.findByTimeBucket(bucket);
    }

    private LocalDateTime findLatestTimeBucket() {
        return serviceLogStat5mRepository.findAll().stream()
                .map(ServiceLogStat5m::getTimeBucket)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("통계 데이터가 없습니다."));
    }
}
