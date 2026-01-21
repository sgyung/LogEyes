package com.logeyes.logdetector.alert.event;

public interface AlertEventPublisher {

    void publishAlertCreated(AlertCreatedEvent event);

}
