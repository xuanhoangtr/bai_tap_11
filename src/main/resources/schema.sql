CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    admin BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    images VARCHAR(500),
    otp_code VARCHAR(10),
    otp_expires_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    category_id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    category_code VARCHAR(100) NOT NULL UNIQUE,
    images VARCHAR(500),
    status BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS videos (
    video_id BIGSERIAL PRIMARY KEY,
    video_code VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    poster VARCHAR(500),
    views INTEGER NOT NULL DEFAULT 0,
    description VARCHAR(1000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    category_id BIGINT NOT NULL REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS shares (
    share_id BIGSERIAL PRIMARY KEY,
    emails VARCHAR(150) NOT NULL,
    shared_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE,
    video_id BIGINT NOT NULL REFERENCES videos(video_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorites (
    favorite_id BIGSERIAL PRIMARY KEY,
    liked_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    video_id BIGINT NOT NULL REFERENCES videos(video_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT uq_favorite_user_video UNIQUE (user_id, video_id)
);

INSERT INTO users (username, password, phone, fullname, email, admin, active, images)
VALUES ('admin', '$2b$12$YKbxiTuh4pt1rF3sXHAt3uRSZC.wleMQhfZCd9BAr1a1wdj6S9Y3e', '0900000000', 'Quản trị viên', 'admin@example.com', TRUE, TRUE, 'avatar.svg')
ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, phone, fullname, email, admin, active, images)
VALUES ('hoang', '$2b$12$YKbxiTuh4pt1rF3sXHAt3uRSZC.wleMQhfZCd9BAr1a1wdj6S9Y3e', '0912345678', 'Trần Xuân Hoàng', 'hoang@example.com', FALSE, TRUE, 'avatar.svg')
ON CONFLICT (username) DO NOTHING;

INSERT INTO categories (category_name, category_code, images) VALUES
('Lập trình Java', 'java', 'category-java.svg'),
('Spring Boot', 'spring', 'category-spring.svg'),
('Cơ sở dữ liệu', 'database', 'category-db.svg')
ON CONFLICT (category_code) DO NOTHING;

INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-001', 'Java căn bản cho người mới bắt đầu', 'java-basic.png', 125, 'Tìm hiểu Java cơ bản.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-002', 'Lập trình hướng đối tượng trong Java', 'java-oop.png', 98, 'Tìm hiểu class, object.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-003', 'Collections và Stream API', 'java-stream.png', 87, 'Tìm hiểu Collection và Stream.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-004', 'Xử lý ngoại lệ trong Java', 'video-java.svg', 76, 'Tìm hiểu xử lý ngoại lệ.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-005', 'Đọc ghi file trong Java', 'video-oop.svg', 69, 'Đọc ghi file cơ bản.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-006', 'Kết nối JDBC trong Java', 'video-jdbc.svg', 64, 'Kết nối database bằng JDBC.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'JAVA-007', 'Đa luồng trong Java', 'video-stream.svg', 58, 'Tìm hiểu lập trình đa luồng.', category_id FROM categories WHERE category_code = 'java'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'SPR-001', 'Spring Boot MVC đầu tiên', 'video-spring.svg', 210, 'Tìm hiểu Spring Boot MVC.', category_id FROM categories WHERE category_code = 'spring'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'SPR-002', 'JdbcTemplate và truy vấn PostgreSQL', 'video-jdbc.svg', 166, 'Kết nối PostgreSQL bằng JDBC.', category_id FROM categories WHERE category_code = 'spring'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'SPR-003', 'Thymeleaf Layout và Fragment', 'video-thymeleaf.svg', 144, 'Tìm hiểu giao diện Thymeleaf.', category_id FROM categories WHERE category_code = 'spring'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'SPR-004', 'Spring Security cơ bản', 'video-spring.svg', 131, 'Tìm hiểu phân quyền Spring Security.', category_id FROM categories WHERE category_code = 'spring'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'DB-001', 'Thiết kế bảng và khóa ngoại', 'video-db.svg', 73, 'Thiết kế bảng và khóa ngoại.', category_id FROM categories WHERE category_code = 'database'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'DB-002', 'Phân trang dữ liệu với SQL', 'video-sql.svg', 61, 'Phân trang dữ liệu với SQL.', category_id FROM categories WHERE category_code = 'database'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
INSERT INTO videos (video_code, title, poster, views, description, category_id)
SELECT 'DB-003', 'Aggregate và thống kê theo nhóm', 'video-count.svg', 55, 'Đếm video theo từng category.', category_id FROM categories WHERE category_code = 'database'
ON CONFLICT (video_code) DO UPDATE SET description = EXCLUDED.description, poster = EXCLUDED.poster;
