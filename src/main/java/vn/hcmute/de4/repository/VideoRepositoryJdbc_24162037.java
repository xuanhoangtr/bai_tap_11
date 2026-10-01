package vn.hcmute.de4.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Video_24162037;

@Repository
public class VideoRepositoryJdbc_24162037 implements VideoRepository_24162037 {
    private final JdbcTemplate jdbc;
    public VideoRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private Video_24162037 map(ResultSet rs, int row) throws SQLException {
        Video_24162037 video = new Video_24162037(rs.getLong("video_id"), rs.getString("video_code"), rs.getString("title"), rs.getString("poster"), rs.getInt("views"), rs.getString("description"), rs.getBoolean("active"), rs.getLong("category_id"), rs.getString("category_name"));
        video.setShareCount(rs.getLong("share_count"));
        video.setLikeCount(rs.getLong("like_count"));
        return video;
    }
    private String baseSql() { return "SELECT v.*, c.category_name, (SELECT COUNT(*) FROM shares s WHERE s.video_id=v.video_id) AS share_count, (SELECT COUNT(*) FROM favorites f WHERE f.video_id=v.video_id) AS like_count FROM videos v JOIN categories c ON c.category_id=v.category_id WHERE v.active=true"; }
    @Override public PageResult_24162037<Video_24162037> findPage(Long categoryId, int page, int size) {
        String filter = categoryId == null ? "" : " AND v.category_id=?";
        String sql = baseSql() + filter + " ORDER BY v.video_id LIMIT ? OFFSET ?";
        List<Video_24162037> rows = categoryId == null ? jdbc.query(sql, this::map, size, page * size) : jdbc.query(sql, this::map, categoryId, size, page * size);
        String countSql = "SELECT COUNT(*) FROM videos v WHERE v.active=true" + filter;
        Long total = categoryId == null ? jdbc.queryForObject(countSql, Long.class) : jdbc.queryForObject(countSql, Long.class, categoryId);
        return new PageResult_24162037<>(rows, Math.max(0, page), size, total == null ? 0 : total);
    }
    @Override public Optional<Video_24162037> findById(Long id) { return jdbc.query(baseSql() + " AND v.video_id=?", this::map, id).stream().findFirst(); }
    @Override public void incrementViews(Long id) { jdbc.update("UPDATE videos SET views=views+1 WHERE video_id=?", id); }
    @Override public long countLikes(Long id) { Long n = jdbc.queryForObject("SELECT COUNT(*) FROM favorites WHERE video_id=?", Long.class, id); return n == null ? 0 : n; }
    @Override public long countShares(Long id) { Long n = jdbc.queryForObject("SELECT COUNT(*) FROM shares WHERE video_id=?", Long.class, id); return n == null ? 0 : n; }
}
