package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.notification.result.NotificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertNotifierRouter {

    private final List<AlertNotifier> notifiers;

    public List<NotificationResult> notify(AlertCreatedEvent event){

        List<NotificationResult> results = new ArrayList<>();

        log.info("[ALERT-NOTIFY-START] alertId={}, severity={}", event.getAlertId(), event.getSeverity());

        for(AlertNotifier notifier : notifiers){

            if(!notifier.support(event.getSeverity())) {
                continue;
            }

            try{
                NotificationResult result = notifier.notify(event);
                results.add(result);

                log.info("[ALERT-NOTIFY-RESULT] channel={}, status={}", result.getChannel(), result.getStatus());

            }catch (Exception e){

                results.add(
                        NotificationResult.fail(notifier.channel(), e.getMessage())
                );

                log.error("[ALERT-NOTIFY-ERROR] channel={}, severity={}", notifier.channel(),e.getMessage());
            }
        }

        return results;
    }
}
