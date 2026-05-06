package com.srotip.inventoryservice.constants;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String BASE_PATH = "/api/v1/inventory";

    public static final String BY_PRODUCT_ID = "/{productId}";

    public static final String CREATE = "/create";

    public static final String REDUCE_STOCK = "/reduce";

    public static final String RESTORE_STOCK = "/restore";
}