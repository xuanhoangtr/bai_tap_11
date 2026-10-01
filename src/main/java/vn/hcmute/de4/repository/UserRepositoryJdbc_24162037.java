package vn.hcmute.de4.repository;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.User_24162037;

@Repository
public class UserRepositoryJdbc_24162037 implements UserRepository_24162037 {
    private final JdbcTemplate jdbc;
    public UserRepositoryJdbc_24162037(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private User_24162037 map(ResultSet rs, int row) throws SQLException {
        Timestamp expiry = rs.getTimestamp("otp_expires_at");
        return new User_24162037(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"),
                rs.getString("phone"), rs.getString("fullname"), rs.getString("email"), rs.getBoolean("admin"),
                rs.getBoolean("active"), rs.getString("images"), rs.getString("otp_code"),
                expiry == null ? null : expiry.toLocalDateTime());
    }

    @Override public Optional<User_24162037> findById(Long id) {
        List<User_24162037> rows = jdbc.query("SELECT * FROM users WHERE user_id = ?", this::map, id);
        return rows.stream().findFirst();
    }
    @Override public Optional<User_24162037> findByUsername(String username) {
        List<User_24162037> rows = jdbc.query("SELECT * FROM users WHERE username = ?", this::map, username);
        return rows.stream().findFirst();
    }
    @Override public Optional<User_24162037> findByEmail(String email) {
        List<User_24162037> rows = jdbc.query("SELECT * FROM users WHERE email = ?", this::map, email);
        return rows.stream().findFirst();
    }
    @Override public PageResult_24162037<User_24162037> findPage(int page, int size) {
        int safePage = Math.max(0, page);
        List<User_24162037> rows = jdbc.query("SELECT * FROM users ORDER BY user_id LIMIT ? OFFSET ?", this::map, size, safePage * size);
        return new PageResult_24162037<>(rows, safePage, size, count());
    }
    @Override public User_24162037 insert(User_24162037 user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            var ps = connection.prepareStatement("INSERT INTO users (username,password,phone,fullname,email,admin,active,images,otp_code,otp_expires_at) VALUES (?,?,?,?,?,?,?,?,?,?)", new String[]{"user_id"});
            ps.setString(1, user.getUsername()); ps.setString(2, user.getPassword()); ps.setString(3, user.getPhone());
            ps.setString(4, user.getFullname()); ps.setString(5, user.getEmail()); ps.setBoolean(6, user.isAdmin());
            ps.setBoolean(7, user.isActive()); ps.setString(8, user.getImages()); ps.setString(9, user.getOtpCode());
            ps.setTimestamp(10, user.getOtpExpiresAt() == null ? null : Timestamp.valueOf(user.getOtpExpiresAt()));
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) user.setUserId(keyHolder.getKey().longValue());
        return user;
    }
    @Override public void update(User_24162037 user) {
        jdbc.update("UPDATE users SET username=?,password=?,phone=?,fullname=?,email=?,admin=?,active=?,images=?,otp_code=?,otp_expires_at=? WHERE user_id=?",
                user.getUsername(), user.getPassword(), user.getPhone(), user.getFullname(), user.getEmail(), user.isAdmin(),
                user.isActive(), user.getImages(), user.getOtpCode(), user.getOtpExpiresAt() == null ? null : Timestamp.valueOf(user.getOtpExpiresAt()), user.getUserId());
    }
    @Override public void deleteById(Long id) { jdbc.update("DELETE FROM users WHERE user_id = ?", id); }
    @Override public long count() { Long value = jdbc.queryForObject("SELECT COUNT(*) FROM users", Long.class); return value == null ? 0 : value; }
}
