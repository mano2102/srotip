package com.srotip.inventoryservice.listeners;

import com.srotip.events.ProductCreatedEvent;
import com.srotip.inventoryservice.constants.KafkaMessage;
import com.srotip.inventoryservice.constants.KafkaTopics;
import com.srotip.inventoryservice.dto.InventoryReduceEventDTO;
import com.srotip.inventoryservice.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryKafkaListener {

    private final InventoryService inventoryService;

    public InventoryKafkaListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = KafkaTopics.PRODUCT_CREATED_TOPIC, groupId = "inventory-group")
    public void handleProductCreated(ProductCreatedEvent event) {

        System.out.println(KafkaMessage.RECEVIED_PRODUCT_EVENT + event.getProductId());

        inventoryService.createInventory(event);
    }

    @KafkaListener(topics = KafkaTopics.ORDER_PLACED_TOPIC, groupId = "inventory-group")
    public void handleOrderPlaced(InventoryReduceEventDTO event) {

        System.out.println(KafkaMessage.RECEVIED_ORDER_EVENT + event.getProductId());

        inventoryService.reduceStock(
                event.getProductId(),
                event.getQuantity());
    }
}