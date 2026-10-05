package vn.hcmute.de4.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.CartItem_24162037;
import vn.hcmute.de4.model.CheckoutForm_24162037;
import vn.hcmute.de4.model.OrderItem_24162037;
import vn.hcmute.de4.model.Order_24162037;

@Repository
public class OrderRepositoryJdbc_24162037 implements OrderRepository_24162037 {
    private final JdbcTemplate jdbc;
    public OrderRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private Order_24162037 mapOrder(ResultSet rs, int row) throws SQLException {
        Order_24162037 order = new Order_24162037(); order.setOrderId(rs.getLong("order_id")); order.setUserId(rs.getLong("user_id"));
        order.setReceiverName(rs.getString("receiver_name")); order.setReceiverPhone(rs.getString("receiver_phone")); order.setShippingAddress(rs.getString("shipping_address"));
        order.setNotes(rs.getString("notes")); order.setPaymentMethod(rs.getString("payment_method")); order.setOrderStatus(rs.getString("order_status")); order.setTotalAmount(rs.getBigDecimal("total_amount"));
        Timestamp created = rs.getTimestamp("created_at"); if (created != null) order.setCreatedAt(created.toLocalDateTime()); return order;
    }
    private List<OrderItem_24162037> findItems(Long orderId) {
        return jdbc.query("SELECT product_id,product_name,unit_price,quantity,line_total FROM de4_order_items WHERE order_id=? ORDER BY order_item_id", (rs, row) -> new OrderItem_24162037((Long) rs.getObject("product_id"), rs.getString("product_name"), rs.getBigDecimal("unit_price"), rs.getInt("quantity"), rs.getBigDecimal("line_total")), orderId);
    }
    @Override public Order_24162037 create(Long userId, CheckoutForm_24162037 form, BigDecimal total, List<CartItem_24162037> items) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            var ps = connection.prepareStatement("INSERT INTO de4_orders(user_id,receiver_name,receiver_phone,shipping_address,notes,payment_method,order_status,total_amount) VALUES (?,?,?,?,?,'COD','PENDING',?)", new String[] {"order_id"});
            ps.setLong(1,userId); ps.setString(2,form.getReceiverName().trim()); ps.setString(3,form.getReceiverPhone().trim()); ps.setString(4,form.getShippingAddress().trim()); ps.setString(5,blankToNull(form.getNotes())); ps.setBigDecimal(6,total); return ps;
        }, keyHolder);
        Number key = keyHolder.getKey(); if (key == null) throw new IllegalStateException("Không tạo được đơn hàng.");
        Long orderId = key.longValue();
        for (CartItem_24162037 item : items) {
            BigDecimal lineTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            jdbc.update("INSERT INTO de4_order_items(order_id,product_id,product_name,unit_price,quantity,line_total) VALUES (?,?,?,?,?,?)", orderId, item.getProduct().getProductId(), item.getProduct().getProductName(), item.getProduct().getPrice(), item.getQuantity(), lineTotal);
        }
        Order_24162037 order = jdbc.queryForObject("SELECT * FROM de4_orders WHERE order_id=?", this::mapOrder, orderId);
        if (order != null) order.setItems(findItems(orderId)); return order;
    }
    @Override public List<Order_24162037> findByUser(Long userId, String status) {
        String sql = "SELECT * FROM de4_orders WHERE user_id=?";
        if (status != null) {
            sql += " AND order_status=?";
            return jdbc.query(sql + " ORDER BY created_at DESC,order_id DESC", this::mapOrder, userId, status);
        }
        return jdbc.query(sql + " ORDER BY created_at DESC,order_id DESC", this::mapOrder, userId);
    }
    @Override public Optional<Order_24162037> findOwnedById(Long orderId, Long userId) {
        List<Order_24162037> rows = jdbc.query("SELECT * FROM de4_orders WHERE order_id=? AND user_id=?", this::mapOrder, orderId, userId);
        if (rows.isEmpty()) return Optional.empty(); Order_24162037 order = rows.get(0); order.setItems(findItems(orderId)); return Optional.of(order);
    }
    private String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
