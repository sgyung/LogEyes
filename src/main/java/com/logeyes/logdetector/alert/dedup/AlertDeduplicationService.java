package com.logeyes.logdetector.alert.dedup;

import java.time.Duration;

public interface AlertDeduplicationService {

    // 같은 key로 이미 장애가 진행 중이면 false
    // 처음 감지되는 장애면 true (그리고 Redis에 기록)
    boolean tryMarkActive(String dedupKey, Duration ttl);

    void clear(String dedupKey);

}
