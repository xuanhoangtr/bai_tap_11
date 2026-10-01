package vn.hcmute.de4.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.CartItem_24162037;
import vn.hcmute.de4.model.Product_24162037;

@Repository
public class CartRepositoryJdbc_24162037 implements CartRepository_24162037 {
    private final JdbcTemplate jdbc;
    public CartRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private CartItem_24162037 map(ResultSet rs, int row) throws SQLException {
        Product_24162037 product = new Product_24162037(rs.getLong("product_id"), rs.getString("product_code"), rs.getString("product_name"), rs.getString("description"), rs.getBigDecimal("price"), rs.getInt("stock"), rs.getString("image"), rs.getString("category_name"), rs.getBoolean("active"));
        return new CartItem_24162037(rs.getLong("cart_item_id"), product, rs.getInt("quantity"));
    }
    private String select() { return "SELECT ci.cart_item_id,ci.quantity,p.product_id,p.product_code,p.product_name,p.description,p.price,p.stock,p.image,p.category_name,p.active FROM de4_cart_items ci JOIN de4_products p ON p.product_id=ci.product_id WHERE ci.user_id=?"; }
    @Override public void lockOwner(Long userId) { jdbc.query("SELECT user_id FROM users WHERE user_id=? FOR UPDATE", (rs, row) -> rs.getLong(1), userId); }
    @Override public List<CartItem_24162037> findItems(Long userId) { return jdbc.query(select() + " ORDER BY ci.cart_item_id", this::map, userId); }
    @Override public List<CartItem_24162037> lockItems(Long userId) { return jdbc.query(select() + " ORDER BY ci.product_id FOR UPDATE OF ci", this::map, userId); }
    @Override public Optional<CartItem_24162037> findItem(Long userId, Long productId) { return jdbc.query(select() + " AND p.product_id=?", this::map, userId, productId).stream().findFirst(); }
    @Override public void addQuantity(Long userId, Long productId, int quantity) {
        jdbc.update("INSERT INTO de4_cart_items(user_id,product_id,quantity) VALUES (?,?,?) ON CONFLICT(user_id,product_id) DO UPDATE SET quantity=de4_cart_items.quantity+EXCLUDED.quantity", userId, productId, quantity);
    }
    @Override public void setQuantity(Long userId, Long productId, int quantity) { jdbc.update("UPDATE de4_cart_items SET quantity=? WHERE user_id=? AND product_id=?", quantity, userId, productId); }
    @Override public void remove(Long userId, Long productId) { jdbc.update("DELETE FROM de4_cart_items WHERE user_id=? AND product_id=?", userId, productId); }
    @Override public void clear(Long userId) { jdbc.update("DELETE FROM de4_cart_items WHERE user_id=?", userId); }
}
