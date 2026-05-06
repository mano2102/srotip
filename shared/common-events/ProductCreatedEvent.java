public class ProductCreatedEvent {

    private Long productId;
    private String sku;
    private Integer initialStock;
    private String category;
    private String brand;

    public ProductCreatedEvent() {
    }

    public ProductCreatedEvent(Long productId, String sku,
            Integer initialStock,
            String category,
            String brand) {
        this.productId = productId;
        this.sku = sku;
        this.initialStock = initialStock;
        this.category = category;
        this.brand = brand;
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

    public Integer getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(Integer initialStock) {
        this.initialStock = initialStock;
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