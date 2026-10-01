package vn.hcmute.de4.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hcmute.de4.model.CartItem_24162037;
import vn.hcmute.de4.model.Product_24162037;
import vn.hcmute.de4.repository.CartRepository_24162037;
import vn.hcmute.de4.repository.ProductRepository_24162037;

@Service
public class CartService_24162037 {
    private final CartRepository_24162037 cartRepository;
    private final ProductRepository_24162037 productRepository;
    public CartService_24162037(CartRepository_24162037 cartRepository, ProductRepository_24162037 productRepository) { this.cartRepository = cartRepository; this.productRepository = productRepository; }
    public List<CartItem_24162037> getItems(Long userId) { return cartRepository.findItems(userId); }
    public BigDecimal total(List<CartItem_24162037> items) { return items.stream().map(CartItem_24162037::getLineTotal).reduce(BigDecimal.ZERO, BigDecimal::add); }
    @Transactional
    public void add(Long userId, Long productId, int requestedQuantity) {
        if (requestedQuantity < 1) throw new IllegalArgumentException("Số lượng phải từ 1 trở lên.");
        cartRepository.lockOwner(userId);
        Product_24162037 product = productRepository.lockActiveById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không còn được bán."));
        int existing = cartRepository.findItem(userId, productId).map(CartItem_24162037::getQuantity).orElse(0);
        validateQuantity(requestedQuantity, product.getStock() - existing);
        cartRepository.addQuantity(userId, productId, requestedQuantity);
    }
    @Transactional
    public void update(Long userId, Long productId, int quantity) {
        cartRepository.lockOwner(userId);
        Product_24162037 product = productRepository.lockActiveById(productId).orElseThrow(() -> new IllegalArgumentException("Sản phẩm không còn được bán."));
        validateQuantity(quantity, product.getStock());
        if (cartRepository.findItem(userId, productId).isEmpty()) throw new IllegalArgumentException("Sản phẩm không còn trong giỏ hàng.");
        cartRepository.setQuantity(userId, productId, quantity);
    }
    @Transactional public void remove(Long userId, Long productId) { cartRepository.lockOwner(userId); cartRepository.remove(userId, productId); }
    public void validateQuantity(int quantity, int max) {
        if (max < 1) throw new IllegalArgumentException("Sản phẩm hiện đã hết hàng.");
        if (quantity < 1 || quantity > max) throw new IllegalArgumentException("Số lượng phải từ 1 đến " + max + " sản phẩm còn trong kho.");
    }
}
