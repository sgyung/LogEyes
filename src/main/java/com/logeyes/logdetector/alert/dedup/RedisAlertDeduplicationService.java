package com.logeyes.logdetector.alert.dedup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisAlertDeduplicationService implements AlertDeduplicationService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean tryMarkActive(String dedupKey, Duration ttl) {

        // SETNX + TTL: 없으면 저장(true), 있으면 저장 안 함(false)
        Boolean success = redisTemplate.opsForValue().setIfAbsent(dedupKey,"1", ttl);

        boolean result = Boolean.TRUE.equals(success);

        if (result) {
            log.info("[ALERT-DEDUP] NEW key={}, ttl={}", dedupKey, ttl);
        } else {
            log.info("[ALERT-DEDUP] DUPLICATE key={} (skip create)", dedupKey);
        }

        return result;
    }

    @Override
    public void clear(String dedupKey) {
        redisTemplate.delete(dedupKey);
        log.info("[ALERT-DEDUP] CLEAR key={}", dedupKey);
    }
}
