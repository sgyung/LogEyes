package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AlertNotifierRouterTest {

    @Test
    void CRITICAL이면_모든_notifier가_호출된다(){
        // given
        AlertNotifier console = Mockito.mock(AlertNotifier.class);
        AlertNotifier slack = Mockito.mock(AlertNotifier.class);
        AlertNotifier email = Mockito.mock(AlertNotifier.class);

        Mockito.when(console.support(AlertSeverity.CRITICAL)).thenReturn(true);
        Mockito.when(slack.support(AlertSeverity.CRITICAL)).thenReturn(true);
        Mockito.when(email.support(AlertSeverity.CRITICAL)).thenReturn(true);

        AlertNotifierRouter router = new AlertNotifierRouter(List.of(console, slack, email));

        AlertCreatedEvent event = new AlertCreatedEvent();
        event.setSeverity(AlertSeverity.CRITICAL);

        // when
        router.notify(event);

        // then
        Mockito.verify(console).notify(event);
        Mockito.verify(slack).notify(event);
        Mockito.verify(email).notify(event);
    }

    @Test
    void LOW_알림은_콘솔만_호출된다() {
        // given
        AlertNotifier console = Mockito.mock(AlertNotifier.class);
        AlertNotifier slack = Mockito.mock(AlertNotifier.class);
        AlertNotifier email = Mockito.mock(AlertNotifier.class);

        Mockito.when(console.support(AlertSeverity.LOW)).thenReturn(true);
        Mockito.when(slack.support(AlertSeverity.LOW)).thenReturn(false);
        Mockito.when(email.support(AlertSeverity.LOW)).thenReturn(false);

        AlertNotifierRouter router = new AlertNotifierRouter(List.of(console, slack, email));
        AlertCreatedEvent event = new AlertCreatedEvent();
        event.setSeverity(AlertSeverity.LOW);

        // when
        router.notify(event);

        // given
        Mockito.verify(console).notify(event);
        Mockito.verify(slack, Mockito.never()).notify(event);
        Mockito.verify(email, Mockito.never()).notify(event);
    }
}
