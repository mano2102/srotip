package com.srotip.productservice.producer;

import com.srotip.productservice.constants.KafkaTopics;
import com.srotip.productservice.event.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProductCreatedEvent(ProductCreatedEvent event) {

        kafkaTemplate.send(KafkaTopics.PRODUCT_CREATED_TOPIC, event.getProductId().toString(), event);

        System.out.println(KafkaTopics.PRODUCT_CREATED_EVENT_SEND + event.getProductId());
    }
}