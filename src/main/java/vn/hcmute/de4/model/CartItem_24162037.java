package vn.hcmute.de4.model;

import java.math.BigDecimal;

public class CartItem_24162037 {
    private Long cartItemId;
    private Product_24162037 product;
    private int quantity;

    public CartItem_24162037() {}
    public CartItem_24162037(Long cartItemId, Product_24162037 product, int quantity) {
        this.cartItemId = cartItemId; this.product = product; this.quantity = quantity;
    }
    public Long getCartItemId() { return cartItemId; }
    public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }
    public Product_24162037 getProduct() { return product; }
    public void setProduct(Product_24162037 product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getAvailableStock() { return product == null ? 0 : product.getStock(); }
    public BigDecimal getLineTotal() { return product.getPrice().multiply(BigDecimal.valueOf(quantity)); }
}
