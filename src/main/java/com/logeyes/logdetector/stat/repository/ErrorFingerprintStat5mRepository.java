package com.logeyes.logdetector.stat.repository;

import com.logeyes.logdetector.stat.domain.ErrorFingerprintStat5m;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ErrorFingerprintStat5mRepository extends JpaRepository<ErrorFingerprintStat5m, Long> {

    List<ErrorFingerprintStat5m>
    findByServiceNameAndEnvironmentAndFingerprintAndTimeBucketBetween(
            String serviceName,
            String environment,
            String fingerprint,
            LocalDateTime from,
            LocalDateTime to
    );

    List<ErrorFingerprintStat5m> findByFingerprintAndTimeBucketBetween(String fingerprint, LocalDateTime timeBucketAfter, LocalDateTime timeBucketBefore);
}
