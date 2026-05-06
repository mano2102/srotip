package com.srotip.inventoryservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", indexes = {
        @Index(name = "idx_product_id", columnList = "productId"),
        @Index(name = "idx_sku", columnList = "sku")
})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    // 🔗 Link to Product Service
    @Column(nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String sku;

    // 📦 Stock Info
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer soldQuantity;

    // 📊 Safety & control
    private Integer lowStockThreshold;

    // 🧾 Optional metadata (from product service sync)
    private String category;
    private String brand;

    private Boolean active;

    // ⏱ audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.availableQuantity == null)
            this.availableQuantity = 0;
        if (this.reservedQuantity == null)
            this.reservedQuantity = 0;
        if (this.soldQuantity == null)
            this.soldQuantity = 0;
        if (this.active == null)
            this.active = true;
    }

    public Inventory() {
    }

    public Inventory(Long inventoryId, Long productId, String sku, Integer totalQuantity, Integer availableQuantity,
            Integer reservedQuantity, Integer soldQuantity, Integer lowStockThreshold, String category, String brand,
            Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.sku = sku;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.soldQuantity = soldQuantity;
        this.lowStockThreshold = lowStockThreshold;
        this.category = category;
        this.brand = brand;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

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

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}