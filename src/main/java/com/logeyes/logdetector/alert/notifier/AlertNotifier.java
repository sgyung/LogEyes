package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;

public interface AlertNotifier {

    void notify(AlertCreatedEvent event);
}
