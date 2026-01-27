package com.logeyes.logdetector.alert.event;

public interface AlertEventPublisher {

    void publishAlertCreated(AlertCreatedEvent event);

    void publishAlertResolved(AlertResolvedEvent event);
}
