package com.srotip.notificationservice.service.impl;

import org.springframework.stereotype.Service;

import com.srotip.notificationservice.events.UserCreatedEvent;
import com.srotip.notificationservice.model.Notification;
import com.srotip.notificationservice.repository.NotificationRepository;
import com.srotip.notificationservice.service.EmailService;
import com.srotip.notificationservice.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final EmailService emailService;

    public NotificationServiceImpl(NotificationRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public void sendNotificationFromEvent(UserCreatedEvent event) {

        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setMessage("Welcome " + event.getName());
        notification.setType("EMAIL");
        notification.setStatus("SENT");
        notification.setUserId(event.getUserId());

        repository.save(notification);
        emailService.sendEmail(
                event.getEmail(),
                "Welcome to our platform",
                "Hello " + event.getName() + " , your account is created successfully.");
    }
}
