package com.srotip.notificationservice.service;

import com.srotip.notificationservice.events.UserCreatedEvent;

public interface NotificationService {
    void sendNotificationFromEvent(UserCreatedEvent event);
}
