package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertNotifierRouter {

    private final List<AlertNotifier> notifiers;

    public void notify(AlertCreatedEvent event){
        for(AlertNotifier notifier : notifiers){

            if(notifier.support(event.getSeverity())) {
                notifier.notify(event);
            }
        }
    }
}
