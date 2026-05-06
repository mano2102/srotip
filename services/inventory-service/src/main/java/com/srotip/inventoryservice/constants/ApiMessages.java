package com.srotip.inventoryservice.constants;

public final class ApiMessages {

    private ApiMessages() {
    }

    // Generic
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String INTERNAL_ERROR = "Internal server error";

    // Inventory
    public static final String INVENTORY_FETCHED = "Inventory fetched successfully";
    public static final String INVENTORY_CREATED = "Inventory created successfully";
    public static final String INVENTORY_UPDATED = "Inventory updated successfully";
    public static final String INVENTORY_NOT_FOUND = "Inventory not found";
    public static final String INVENTORY_NOT_FOUND_WITH_ID = "Inventory not found for productId: ";

    // Stock
    public static final String STOCK_REDUCED = "Stock reduced successfully";
    public static final String STOCK_ADDED = "Stock added successfully";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock available";
    public static final String STOCK_RESTORED = "Stock restored successfully";

    // Kafka Message
    public static final String RECEVIED_PRODUCT_EVENT = "🔥 Received Product Event: ";
}