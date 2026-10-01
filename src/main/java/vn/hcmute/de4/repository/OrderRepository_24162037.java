package vn.hcmute.de4.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import vn.hcmute.de4.model.CartItem_24162037;
import vn.hcmute.de4.model.CheckoutForm_24162037;
import vn.hcmute.de4.model.Order_24162037;

public interface OrderRepository_24162037 {
    Order_24162037 create(Long userId, CheckoutForm_24162037 form, BigDecimal total, List<CartItem_24162037> items);
    List<Order_24162037> findByUser(Long userId);
    Optional<Order_24162037> findOwnedById(Long orderId, Long userId);
}
