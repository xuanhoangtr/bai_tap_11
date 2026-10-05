-- E-commerce tables use a project prefix because this local database is shared
-- by several coursework projects.
CREATE TABLE IF NOT EXISTS de4_products (
    product_id BIGSERIAL PRIMARY KEY,
    product_code VARCHAR(40) NOT NULL UNIQUE,
    product_name VARCHAR(160) NOT NULL,
    description VARCHAR(1000),
    price NUMERIC(12,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    image VARCHAR(500),
    category_name VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS de4_cart_items (
    cart_item_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES de4_products(product_id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_de4_cart_user_product UNIQUE (user_id, product_id)
);

CREATE TABLE IF NOT EXISTS de4_orders (
    order_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE RESTRICT,
    receiver_name VARCHAR(100) NOT NULL,
    receiver_phone VARCHAR(20) NOT NULL,
    shipping_address VARCHAR(300) NOT NULL,
    notes VARCHAR(500),
    payment_method VARCHAR(20) NOT NULL DEFAULT 'COD' CHECK (payment_method = 'COD'),
    order_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    total_amount NUMERIC(12,2) NOT NULL CHECK (total_amount >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS de4_order_items (
    order_item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES de4_orders(order_id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES de4_products(product_id) ON DELETE SET NULL,
    product_name VARCHAR(160) NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    line_total NUMERIC(12,2) NOT NULL CHECK (line_total >= 0)
);

CREATE INDEX IF NOT EXISTS idx_de4_cart_items_user ON de4_cart_items(user_id);
CREATE INDEX IF NOT EXISTS idx_de4_orders_user_created ON de4_orders(user_id, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_de4_orders_user_status_created ON de4_orders(user_id, order_status, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_de4_order_items_order ON de4_order_items(order_id);

INSERT INTO de4_products (product_code, product_name, description, price, stock, image, category_name) VALUES
('STAND-001', 'Giá đỡ laptop nhôm', 'Giá đỡ gấp gọn, điều chỉnh độ cao, phù hợp góc học tập và làm việc.', 289000, 18, 'product-laptop.jpg', 'Phụ kiện học tập'),
('KEY-001', 'Bàn phím cơ mini', 'Bàn phím cơ layout nhỏ gọn, kết nối USB, phù hợp lập trình.', 759000, 12, 'product-keyboard.jpg', 'Thiết bị máy tính'),
('HEAD-001', 'Tai nghe học tập', 'Tai nghe chụp tai có micro, âm thanh rõ khi học trực tuyến.', 429000, 25, 'product-headphones.jpg', 'Thiết bị học tập'),
('CAM-001', 'Webcam Full HD', 'Webcam 1080p có nắp che riêng tư, dùng cho lớp học trực tuyến.', 599000, 9, NULL, 'Thiết bị học tập'),
('USB-064', 'USB 64GB', 'USB 3.0 dung lượng 64GB để lưu mã nguồn và tài liệu.', 159000, 30, 'product-usb.jpg', 'Lưu trữ'),
('BOOK-001', 'Sách Java thực hành', 'Giáo trình thực hành Java với ví dụ hướng đối tượng và JDBC.', 245000, 14, NULL, 'Sách và tài liệu')
ON CONFLICT (product_code) DO NOTHING;

-- Replace only the original demo illustrations. Existing admin-uploaded photos stay untouched.
UPDATE de4_products SET image='product-laptop.jpg' WHERE product_code='STAND-001' AND image='product-stand.svg';
UPDATE de4_products SET image='product-keyboard.jpg' WHERE product_code='KEY-001' AND image='product-keyboard.svg';
UPDATE de4_products SET image='product-headphones.jpg' WHERE product_code='HEAD-001' AND image='product-headphones.svg';
UPDATE de4_products SET image=NULL WHERE product_code='CAM-001' AND image='product-webcam.svg';
UPDATE de4_products SET image='product-usb.jpg' WHERE product_code='USB-064' AND image='product-usb.svg';
UPDATE de4_products SET image=NULL WHERE product_code='BOOK-001' AND image='product-book.svg';
