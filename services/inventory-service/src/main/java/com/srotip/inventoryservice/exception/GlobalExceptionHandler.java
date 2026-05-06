package com.srotip.inventoryservice.exception;

import com.srotip.inventoryservice.constants.ApiMessages;
import com.srotip.inventoryservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ================= INVENTORY NOT FOUND =================
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(InventoryNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ApiMessages.INVENTORY_NOT_FOUND));
    }

    // ================= INSUFFICIENT STOCK =================
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<?>> handleStock(InsufficientStockException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ApiMessages.INSUFFICIENT_STOCK));
    }

    // ================= GENERIC ERROR =================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ApiMessages.INTERNAL_ERROR));
    }
}