package com.logeyes.logdetector.api.stat;

import com.logeyes.logdetector.stat.dto.ServiceLogStatResponse;
import com.logeyes.logdetector.stat.service.ServiceLogStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Stat API", description = "서비스 로그 통계 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class ServiceLogStatController {

    private final ServiceLogStatService serviceLogStatService;

    @Operation(
            summary = "서비스 로그 통계 조회",
            description = "특정 timeBucket 기준 5분 단위 서비스 로그 통계를 조회합니다. timeBucket 미지정 시 최신 기준으로 조회합니다."
    )
    @GetMapping
    public List<ServiceLogStatResponse> byTimeBucket(
            @RequestParam(required = false) LocalDateTime timeBucket
    ) {
        return serviceLogStatService.getStats(timeBucket)
                .stream()
                .map(ServiceLogStatResponse::new)
                .toList();
    }

}
