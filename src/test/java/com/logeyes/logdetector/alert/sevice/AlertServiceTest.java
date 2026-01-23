package com.logeyes.logdetector.alert.sevice;

import com.logeyes.logdetector.alert.domain.Alert;
import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.domain.AlertStatus;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertEventPublisher;
import com.logeyes.logdetector.alert.repository.AlertRepository;
import com.logeyes.logdetector.alert.service.AlertServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @Mock
    AlertRepository alertRepository;

    @Mock
    AlertEventPublisher  alertEventPublisher;

    @InjectMocks
    AlertServiceImpl alertService;

    @Test
    void 알림_생성_성공(){
        // given
        Alert alert = Alert.create(
                "CB_api",
                "prod",
                "fingerprint",
                AlertSeverity.MEDIUM,
                300,
                5,
                10);

        when(alertRepository.save(any(Alert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Alert saved = alertService.createAlert(alert);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getServiceName()).isEqualTo("CB_api");

        verify(alertRepository).save(any(Alert.class));
        verify(alertEventPublisher).publishAlertCreated(any(AlertCreatedEvent.class));
    }

    @Test
    void 알림_단건_조회_성공(){
        // given
        Alert alert = mock(Alert.class);

        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));

        // when
        Alert result = alertService.getAlert(1L);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 알림_없으면_예외() {
        // given
        when(alertRepository.findById(1L))
                .thenReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> alertService.getAlert(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상태별_알림_조회() {
        // given
        when(alertRepository.findByStatus(AlertStatus.DETECTED))
                .thenReturn(List.of(mock(Alert.class)));

        // when
        List<Alert> alerts = alertService.getAlertByStatus(AlertStatus.DETECTED);

        // then
        assertThat(alerts).hasSize(1);
    }

    @Test
    void 알림_해결_처리() {
        // given
        Alert alert = mock(Alert.class);

        when(alertRepository.findById(1L))
                .thenReturn(Optional.of(alert));

        // when
        alertService.resolvedAlert(1L);

        // then
        verify(alert).resolve();
    }
}
