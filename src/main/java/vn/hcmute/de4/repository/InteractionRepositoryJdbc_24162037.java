package vn.hcmute.de4.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InteractionRepositoryJdbc_24162037 implements InteractionRepository_24162037 {
    private final JdbcTemplate jdbc;
    public InteractionRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    @Override public boolean isLiked(Long userId, Long videoId) {
        try { Integer n = jdbc.queryForObject("SELECT 1 FROM favorites WHERE user_id=? AND video_id=?", Integer.class, userId, videoId); return n != null; }
        catch (EmptyResultDataAccessException e) { return false; }
    }
    @Override public void addFavorite(Long userId, Long videoId) { jdbc.update("INSERT INTO favorites(user_id,video_id) VALUES (?,?) ON CONFLICT (user_id,video_id) DO NOTHING", userId, videoId); }
    @Override public void removeFavorite(Long userId, Long videoId) { jdbc.update("DELETE FROM favorites WHERE user_id=? AND video_id=?", userId, videoId); }
    @Override public void addShare(Long userId, Long videoId, String email) { jdbc.update("INSERT INTO shares(user_id,video_id,emails) VALUES (?,?,?)", userId, videoId, email); }
}
