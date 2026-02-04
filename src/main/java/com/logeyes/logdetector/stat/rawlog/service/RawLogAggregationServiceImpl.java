package com.logeyes.logdetector.stat.rawlog.service;
import com.logeyes.logdetector.stat.domain.ServiceLogStat5m;
import com.logeyes.logdetector.stat.domain.ErrorFingerprintStat5m;
import com.logeyes.logdetector.stat.repository.ServiceLogStat5mRepository;
import com.logeyes.logdetector.stat.repository.ErrorFingerprintStat5mRepository;
import com.logeyes.logdetector.stat.rawlog.dto.RawLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RawLogAggregationServiceImpl implements RawLogAggregationService {

    private final ServiceLogStat5mRepository serviceLogStatRepository;
    private final ErrorFingerprintStat5mRepository fingerprintStatRepository;

    @Override
    @Transactional
    public void aggregate(RawLogEvent message) {

        LocalDateTime bucket =
                ErrorFingerprintStat5m.normalizeTo5MinBucket(message.getOccurredAt());

        // 1️⃣ 서비스 단위 집계
        ServiceLogStat5m serviceStat =
                serviceLogStatRepository
                        .findByServiceNameAndEnvironmentAndTimeBucket(
                                message.getServiceName(),
                                message.getEnvironment(),
                                bucket
                        )
                        .orElseGet(() -> ServiceLogStat5m.create(
                                bucket,
                                message.getServiceName(),
                                message.getEnvironment()
                        ));

        serviceStat.increaseTotal();

        if ("ERROR".equalsIgnoreCase(message.getLevel())) {
            serviceStat.increaseError();
        }

        if ("WARN".equalsIgnoreCase(message.getLevel())) {
            serviceStat.increaseWarn();
        }

        serviceLogStatRepository.save(serviceStat);

        // 2️⃣ fingerprint 집계 (ERROR만)
        if ("ERROR".equalsIgnoreCase(message.getLevel())) {

            ErrorFingerprintStat5m fingerprintStat =
                    fingerprintStatRepository
                            .findByServiceNameAndEnvironmentAndFingerprintAndTimeBucket(
                                    message.getServiceName(),
                                    message.getEnvironment(),
                                    message.getFingerprint(),
                                    bucket
                            )
                            .orElseGet(() -> ErrorFingerprintStat5m.create(
                                    bucket,
                                    message.getServiceName(),
                                    message.getEnvironment(),
                                    message.getFingerprint(),
                                    0
                            ));

            fingerprintStat.increaseErrorCount();
            fingerprintStatRepository.save(fingerprintStat);
        }

        log.debug("[RAW-LOG] 집계 완료 service={}, level={}, fingerprint={}",
                message.getServiceName(), message.getLevel(), message.getFingerprint());
    }
}
