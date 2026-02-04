package com.logeyes.logdetector.stat.repository;

import com.logeyes.logdetector.stat.domain.ErrorFingerprintStat5m;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ErrorFingerprintStat5mRepository extends JpaRepository<ErrorFingerprintStat5m, Long> {

    /**
     * 특정 fingerprint의 최근 통계 조회 (AI 컨텍스트용)
     */
    List<ErrorFingerprintStat5m>
    findByServiceNameAndEnvironmentAndFingerprintAndTimeBucketBetween(
            String serviceName,
            String environment,
            String fingerprint,
            LocalDateTime from,
            LocalDateTime to
    );

    /**
     * 5분 버킷 단건 조회 (집계 시 upsert 용도)
     */
    Optional<ErrorFingerprintStat5m>
    findByServiceNameAndEnvironmentAndFingerprintAndTimeBucket(
            String serviceName,
            String environment,
            String fingerprint,
            LocalDateTime timeBucket
    );
}
