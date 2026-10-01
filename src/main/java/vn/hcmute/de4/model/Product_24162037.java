package vn.hcmute.de4.model;

import java.math.BigDecimal;

public class Product_24162037 {
    private Long productId;
    private String productCode;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private String image;
    private String categoryName;
    private boolean active;

    public Product_24162037() {}
    public Product_24162037(Long productId, String productCode, String productName, String description, BigDecimal price,
                            int stock, String image, String categoryName, boolean active) {
        this.productId = productId; this.productCode = productCode; this.productName = productName; this.description = description;
        this.price = price; this.stock = stock; this.image = image; this.categoryName = categoryName; this.active = active;
    }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
