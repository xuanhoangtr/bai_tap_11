package vn.hcmute.de4.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hcmute.de4.model.CartItem_24162037;
import vn.hcmute.de4.model.CheckoutForm_24162037;
import vn.hcmute.de4.model.Order_24162037;
import vn.hcmute.de4.model.OrderStatus_24162037;
import vn.hcmute.de4.model.Product_24162037;
import vn.hcmute.de4.repository.CartRepository_24162037;
import vn.hcmute.de4.repository.OrderRepository_24162037;
import vn.hcmute.de4.repository.ProductRepository_24162037;

@Service
public class OrderService_24162037 {
    private final CartRepository_24162037 cartRepository;
    private final ProductRepository_24162037 productRepository;
    private final OrderRepository_24162037 orderRepository;
    public OrderService_24162037(CartRepository_24162037 cartRepository, ProductRepository_24162037 productRepository, OrderRepository_24162037 orderRepository) {
        this.cartRepository = cartRepository; this.productRepository = productRepository; this.orderRepository = orderRepository;
    }
    @Transactional
    public Order_24162037 checkout(Long userId, CheckoutForm_24162037 form) {
        validateForm(form);
        cartRepository.lockOwner(userId);
        List<CartItem_24162037> items = cartRepository.lockItems(userId);
        if (items.isEmpty()) throw new IllegalArgumentException("Giỏ hàng đang trống.");
        List<Long> productIds = items.stream().map(item -> item.getProduct().getProductId()).distinct().sorted().toList();
        List<Product_24162037> lockedProducts = productRepository.lockActiveByIds(productIds);
        Map<Long, Product_24162037> products = lockedProducts.stream().collect(Collectors.toMap(Product_24162037::getProductId, Function.identity()));
        if (products.size() != productIds.size()) throw new IllegalArgumentException("Có sản phẩm trong giỏ không còn được bán. Vui lòng cập nhật giỏ hàng.");
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24162037 item : items) {
            Product_24162037 product = products.get(item.getProduct().getProductId());
            if (item.getQuantity() > product.getStock()) throw new IllegalArgumentException(product.getProductName() + " chỉ còn " + product.getStock() + " sản phẩm. Hãy sửa số lượng trong giỏ.");
            item.setProduct(product);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        Order_24162037 order = orderRepository.create(userId, form, total, items);
        for (CartItem_24162037 item : items) {
            if (!productRepository.decreaseStock(item.getProduct().getProductId(), item.getQuantity())) throw new IllegalArgumentException("Kho hàng vừa thay đổi. Vui lòng thử thanh toán lại.");
        }
        cartRepository.clear(userId);
        return order;
    }
    public List<Order_24162037> findOrders(Long userId, String status) {
        String statusCode = OrderStatus_24162037.fromCode(status)
                .map(OrderStatus_24162037::getCode)
                .orElse(null);
        return orderRepository.findByUser(userId, statusCode);
    }
    public Optional<Order_24162037> findOrder(Long userId, Long orderId) { return orderRepository.findOwnedById(orderId, userId); }
    private void validateForm(CheckoutForm_24162037 form) {
        if (form == null || blank(form.getReceiverName()) || blank(form.getReceiverPhone()) || blank(form.getShippingAddress())) throw new IllegalArgumentException("Vui lòng nhập tên người nhận, số điện thoại và địa chỉ giao hàng.");
        if (form.getReceiverName().trim().length() > 100 || form.getReceiverPhone().trim().length() > 20 || form.getShippingAddress().trim().length() > 300 || (form.getNotes() != null && form.getNotes().trim().length() > 500)) throw new IllegalArgumentException("Thông tin giao hàng vượt quá độ dài cho phép.");
    }
    private boolean blank(String value) { return value == null || value.isBlank(); }
}
