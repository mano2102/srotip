package com.srotip.inventoryservice.dto;

public class InventoryResponseDTO {

    private Long productId;
    private String sku;

    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;
    private Integer soldQuantity;

    private String category;
    private String brand;
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    public Integer getReservedQuantity() {
        return reservedQuantity;
    }
    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
    public Integer getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public Integer getSoldQuantity() {
        return soldQuantity;
    }
    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

   
}