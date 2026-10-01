package vn.hcmute.de4.repository;

import java.util.List;
import java.util.Optional;
import vn.hcmute.de4.model.CartItem_24162037;

public interface CartRepository_24162037 {
    void lockOwner(Long userId);
    List<CartItem_24162037> findItems(Long userId);
    List<CartItem_24162037> lockItems(Long userId);
    Optional<CartItem_24162037> findItem(Long userId, Long productId);
    void addQuantity(Long userId, Long productId, int quantity);
    void setQuantity(Long userId, Long productId, int quantity);
    void remove(Long userId, Long productId);
    void clear(Long userId);
}
