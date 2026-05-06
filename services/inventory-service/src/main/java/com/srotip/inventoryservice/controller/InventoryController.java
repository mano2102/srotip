package com.srotip.inventoryservice.controller;

import com.srotip.inventoryservice.constants.ApiMessages;
import com.srotip.inventoryservice.constants.ApiPaths;
import com.srotip.inventoryservice.dto.ApiResponse;
import com.srotip.inventoryservice.dto.InventoryResponseDTO;
import com.srotip.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // ================= GET =================
    @GetMapping(ApiPaths.BY_PRODUCT_ID)
    public ResponseEntity<ApiResponse<InventoryResponseDTO>> get(@PathVariable Long productId) {

        return ResponseEntity.ok(
                ApiResponse.success(ApiMessages.INVENTORY_FETCHED,
                        service.getInventoryByProductId(productId)));
    }

    // ================= CREATE =================
    @PostMapping(ApiPaths.CREATE)
    public ResponseEntity<ApiResponse<Void>> create(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        // service.createInventory(productId, quantity, null);

        return ResponseEntity.ok(
                ApiResponse.success(ApiMessages.INVENTORY_CREATED, null));
    }

    // ================= REDUCE =================
    @PostMapping(ApiPaths.REDUCE_STOCK)
    public ResponseEntity<ApiResponse<Void>> reduce(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        service.reduceStock(productId, quantity);

        return ResponseEntity.ok(
                ApiResponse.success(ApiMessages.STOCK_REDUCED, null));
    }

    // ================= RESTORE =================
    @PostMapping(ApiPaths.RESTORE_STOCK)
    public ResponseEntity<ApiResponse<Void>> restore(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        service.restoreStock(productId, quantity);

        return ResponseEntity.ok(
                ApiResponse.success(ApiMessages.STOCK_RESTORED, null));
    }
}