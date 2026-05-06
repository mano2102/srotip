package com.srotip.inventoryservice.service.impl;

import com.srotip.events.ProductCreatedEvent;
import com.srotip.inventoryservice.constants.ApiMessages;
import com.srotip.inventoryservice.dto.InventoryResponseDTO;
import com.srotip.inventoryservice.exception.InsufficientStockException;
import com.srotip.inventoryservice.exception.InventoryNotFoundException;
import com.srotip.inventoryservice.model.Inventory;
import com.srotip.inventoryservice.repository.InventoryRepository;
import com.srotip.inventoryservice.service.InventoryService;

import java.util.Optional;

import org.apache.kafka.common.protocol.ApiMessage;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    // ================= GET =================
    @Override
    public InventoryResponseDTO getInventoryByProductId(Long productId) {

        Inventory inv = repository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(
                        ApiMessages.INVENTORY_NOT_FOUND_WITH_ID + productId));

        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setProductId(inv.getProductId());
        dto.setSku(inv.getSku());
        dto.setAvailableQuantity(inv.getAvailableQuantity());
        dto.setReservedQuantity(inv.getReservedQuantity());
        dto.setTotalQuantity(inv.getTotalQuantity());
        dto.setSoldQuantity(inv.getSoldQuantity());

        return dto;
    }

    @Override
    public void createInventory(ProductCreatedEvent event) {

        if (event.getSku() == null) {
            throw new IllegalArgumentException("SKU cannot be null");
        }
        String sku = event.getSku();
        int initialStock = event.getInitialStock();
        Long productId = event.getProductId();
        String brand = event.getBrand();
        String category = event.getCategory();
        System.out.println("Initial Quanity:" + event.getInitialStock());

        Optional<Inventory> exists = repository.findBySku(sku);

        if (exists.isPresent()) {
            System.out.println("⚠️ Inventory already exists for SKU: " + sku);
            Inventory update = exists.get();
            update.setAvailableQuantity(update.getAvailableQuantity() + initialStock);
            update.setBrand(brand);
            update.setCategory(category);
            System.out.println(update.getAvailableQuantity() + initialStock);
            repository.save(update);
            return; // idempotent behavior
        }

        Inventory inv = new Inventory();
        inv.setProductId(productId);
        inv.setTotalQuantity(initialStock);
        inv.setAvailableQuantity(initialStock);
        inv.setReservedQuantity(0);
        inv.setSoldQuantity(0);
        inv.setBrand(brand);
        inv.setCategory(category);
        inv.setSku(sku);

        repository.save(inv);
    }

    // ================= REDUCE STOCK =================
    @Override
    public void reduceStock(Long productId, Integer quantity) {

        Inventory inv = repository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(ApiMessages.INVENTORY_NOT_FOUND));

        if (inv.getAvailableQuantity() < quantity) {
            throw new InsufficientStockException(ApiMessages.INSUFFICIENT_STOCK);
        }

        inv.setAvailableQuantity(inv.getAvailableQuantity() - quantity);
        inv.setReservedQuantity(inv.getReservedQuantity() + quantity);

        repository.save(inv);
    }

    // ================= RESTORE STOCK =================
    @Override
    public void restoreStock(Long productId, Integer quantity) {

        Inventory inv = repository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(ApiMessages.INVENTORY_NOT_FOUND));

        inv.setAvailableQuantity(inv.getAvailableQuantity() + quantity);
        inv.setReservedQuantity(inv.getReservedQuantity() - quantity);

        repository.save(inv);
    }
}