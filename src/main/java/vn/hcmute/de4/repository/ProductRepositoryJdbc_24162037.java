package vn.hcmute.de4.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Product_24162037;

@Repository
public class ProductRepositoryJdbc_24162037 implements ProductRepository_24162037 {
    private final JdbcTemplate jdbc;
    public ProductRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private Product_24162037 map(ResultSet rs, int row) throws SQLException {
        return new Product_24162037(rs.getLong("product_id"), rs.getString("product_code"), rs.getString("product_name"), rs.getString("description"),
                rs.getBigDecimal("price"), rs.getInt("stock"), rs.getString("image"), rs.getString("category_name"), rs.getBoolean("active"));
    }
    @Override public PageResult_24162037<Product_24162037> findActivePage(int page, int size) {
        int safePage = Math.max(0, page);
        List<Product_24162037> rows = jdbc.query("SELECT * FROM de4_products WHERE active=true ORDER BY product_id LIMIT ? OFFSET ?", this::map, size, safePage * size);
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM de4_products WHERE active=true", Long.class);
        return new PageResult_24162037<>(rows, safePage, size, count == null ? 0 : count);
    }
    @Override public PageResult_24162037<Product_24162037> findAdminPage(int page, int size) {
        int safePage = Math.max(0, page);
        List<Product_24162037> rows = jdbc.query("SELECT * FROM de4_products ORDER BY product_id DESC LIMIT ? OFFSET ?", this::map, size, safePage * size);
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM de4_products", Long.class);
        return new PageResult_24162037<>(rows, safePage, size, count == null ? 0 : count);
    }
    @Override public Optional<Product_24162037> findActiveById(Long id) {
        return jdbc.query("SELECT * FROM de4_products WHERE product_id=? AND active=true", this::map, id).stream().findFirst();
    }
    @Override public Optional<Product_24162037> findAdminById(Long id) {
        return jdbc.query("SELECT * FROM de4_products WHERE product_id=?", this::map, id).stream().findFirst();
    }
    @Override public Product_24162037 insert(Product_24162037 product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            var ps = connection.prepareStatement("INSERT INTO de4_products(product_code,product_name,description,price,stock,image,category_name,active) VALUES (?,?,?,?,?,?,?,?)", new String[] {"product_id"});
            ps.setString(1, product.getProductCode()); ps.setString(2, product.getProductName()); ps.setString(3, product.getDescription());
            ps.setBigDecimal(4, product.getPrice()); ps.setInt(5, product.getStock()); ps.setString(6, product.getImage());
            ps.setString(7, product.getCategoryName()); ps.setBoolean(8, product.isActive()); return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null) throw new IllegalStateException("Không tạo được sản phẩm.");
        product.setProductId(keyHolder.getKey().longValue());
        return product;
    }
    @Override public void update(Product_24162037 product) {
        jdbc.update("UPDATE de4_products SET product_code=?,product_name=?,description=?,price=?,stock=?,image=?,category_name=?,active=? WHERE product_id=?",
                product.getProductCode(), product.getProductName(), product.getDescription(), product.getPrice(), product.getStock(), product.getImage(), product.getCategoryName(), product.isActive(), product.getProductId());
    }
    @Override public void deactivate(Long id) {
        jdbc.update("UPDATE de4_products SET active=false WHERE product_id=?", id);
        jdbc.update("DELETE FROM de4_cart_items WHERE product_id=?", id);
    }
    @Override public Optional<Product_24162037> lockActiveById(Long id) {
        return jdbc.query("SELECT * FROM de4_products WHERE product_id=? AND active=true FOR UPDATE", this::map, id).stream().findFirst();
    }
    @Override public List<Product_24162037> lockActiveByIds(List<Long> ids) {
        if (ids.isEmpty()) return List.of();
        String marks = String.join(",", java.util.Collections.nCopies(ids.size(), "?"));
        List<Object> args = new ArrayList<>(ids);
        return jdbc.query("SELECT * FROM de4_products WHERE active=true AND product_id IN (" + marks + ") ORDER BY product_id FOR UPDATE", this::map, args.toArray());
    }
    @Override public boolean decreaseStock(Long id, int quantity) {
        return jdbc.update("UPDATE de4_products SET stock=stock-? WHERE product_id=? AND stock>=? AND active=true", quantity, id, quantity) == 1;
    }
}
