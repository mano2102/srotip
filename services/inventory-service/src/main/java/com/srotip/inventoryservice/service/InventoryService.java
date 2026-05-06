package com.srotip.inventoryservice.service;

import com.srotip.inventoryservice.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO getInventoryByProductId(Long productId);

    void createInventory(Long productId, Integer initialStock);

    void reduceStock(Long productId, Integer quantity);

    void restoreStock(Long productId, Integer quantity);
}