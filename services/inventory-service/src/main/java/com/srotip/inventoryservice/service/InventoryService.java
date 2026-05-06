package com.srotip.inventoryservice.service;

import com.srotip.events.ProductCreatedEvent;
import com.srotip.inventoryservice.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO getInventoryByProductId(Long productId);

    void createInventory(ProductCreatedEvent event);

    void reduceStock(Long productId, Integer quantity);

    void restoreStock(Long productId, Integer quantity);
}