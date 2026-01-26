package com.logeyes.logdetector.stat.repository;

import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceLogStat5mRepository extends JpaRepository<ServiceLogStat5m, Long> {

    List<ServiceLogStat5m> findByTimeBucket(LocalDateTime timeBucket);
    
}
