package com.srotip.userservice.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.srotip.userservice.event.UserCreatedEvent;

@Component
public class UserEventProducer {
    private final KafkaTemplate<String, UserCreatedEvent> kakfaTemplate;

    public UserEventProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kakfaTemplate = kafkaTemplate;
    }

    public void publicUserCreatedEvent(UserCreatedEvent event) {
        this.kakfaTemplate.send("user-created", event);
    }

}
