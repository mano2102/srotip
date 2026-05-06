package com.srotip.notificationservice.listener;



import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.srotip.notificationservice.events.UserCreatedEvent;
import com.srotip.notificationservice.service.NotificationService;

@Component
public class UserEventListener {

    private final NotificationService notificationService;

    public UserEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "user-created", groupId = "notification-group")
    public void consume(UserCreatedEvent event) {

        System.out.println("🔥 Received event: " + event.getEmail());

        // convert event → notification request
        notificationService.sendNotificationFromEvent(event);
    }
}