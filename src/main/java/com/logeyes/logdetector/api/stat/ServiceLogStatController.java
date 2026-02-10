package com.logeyes.logdetector.api.stat;

import com.logeyes.logdetector.stat.dto.ServiceLogStatResponse;
import com.logeyes.logdetector.stat.service.ServiceLogStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class ServiceLogStatController {

    private final ServiceLogStatService serviceLogStatService;

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
