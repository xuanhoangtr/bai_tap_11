package vn.hcmute.de4.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order_24162037 {
    private Long orderId;
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String notes;
    private String paymentMethod;
    private String orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItem_24162037> items = new ArrayList<>();

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public String getOrderStatusLabel() {
        return OrderStatus_24162037.fromCode(orderStatus)
                .map(OrderStatus_24162037::getLabel)
                .orElse(orderStatus == null || orderStatus.isBlank() ? "Chưa cập nhật" : orderStatus);
    }
    public String getOrderStatusBadgeColor() {
        return OrderStatus_24162037.fromCode(orderStatus)
                .map(OrderStatus_24162037::getBadgeColor)
                .orElse("secondary");
    }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderItem_24162037> getItems() { return items; }
    public void setItems(List<OrderItem_24162037> items) { this.items = items; }
}
