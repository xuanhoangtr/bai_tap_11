package vn.hcmute.de4.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.Category_24162037;

@Repository
public class CategoryRepositoryJdbc_24162037 implements CategoryRepository_24162037 {
    private final JdbcTemplate jdbc;
    public CategoryRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private Category_24162037 map(ResultSet rs, int row) throws SQLException {
        Category_24162037 c = new Category_24162037(rs.getLong("category_id"), rs.getString("category_name"),
                rs.getString("category_code"), rs.getString("images"), rs.getBoolean("status"));
        try { c.setVideoCount(rs.getLong("video_count")); } catch (SQLException ignored) { }
        return c;
    }
    @Override public List<Category_24162037> findAllWithVideoCount() {
        return jdbc.query("SELECT c.*, COUNT(v.video_id) AS video_count FROM categories c LEFT JOIN videos v ON v.category_id=c.category_id AND v.active=true GROUP BY c.category_id ORDER BY c.category_id", this::map);
    }
    @Override public Optional<Category_24162037> findById(Long id) {
        return jdbc.query("SELECT * FROM categories WHERE category_id=?", (rs, row) -> new Category_24162037(rs.getLong("category_id"), rs.getString("category_name"), rs.getString("category_code"), rs.getString("images"), rs.getBoolean("status")), id).stream().findFirst();
    }
}
