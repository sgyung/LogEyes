package com.logeyes.logdetector.notification.result;

import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.domain.NotificationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResult {

    private NotificationChannel channel;
    private NotificationStatus status;
    private String errorMessage;

    public NotificationResult(NotificationChannel channel, NotificationStatus status, String errorMessage) {
        this.channel = channel;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static NotificationResult success(NotificationChannel channel) {
        return new NotificationResult(channel, NotificationStatus.SUCCESS, null);
    }

    public static NotificationResult fail(NotificationChannel channel, String errorMessage) {
        return new NotificationResult(channel, NotificationStatus.FAIL, errorMessage);
    }

    public boolean isSuccess(){
        return this.status == NotificationStatus.SUCCESS;
    }
}
