package vn.hcmute.de4.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductImageStorage_24162037 {
    private static final long MAX_BYTES = 5L * 1024 * 1024;
    private static final long MAX_PIXELS = 40_000_000L;
    private final Path root = Path.of(System.getProperty("user.dir"), "uploads", "products").toAbsolutePath().normalize();

    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        if (file.getSize() > MAX_BYTES) throw new IllegalArgumentException("Ảnh không được lớn hơn 5 MB.");
        String extension;
        try (ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream())) {
            if (input == null) throw new IllegalArgumentException("Tệp tải lên không phải ảnh hợp lệ.");
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) throw new IllegalArgumentException("Chỉ nhận ảnh JPEG hoặc PNG.");
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                String format = reader.getFormatName().toLowerCase(Locale.ROOT);
                if (!format.equals("jpg") && !format.equals("jpeg") && !format.equals("png")) {
                    throw new IllegalArgumentException("Chỉ nhận ảnh JPEG hoặc PNG.");
                }
                long pixels = (long) reader.getWidth(0) * reader.getHeight(0);
                if (pixels <= 0 || pixels > MAX_PIXELS) throw new IllegalArgumentException("Kích thước ảnh vượt quá giới hạn cho phép.");
                extension = format.equals("png") ? "png" : "jpg";
            } finally {
                reader.dispose();
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Không đọc được tệp ảnh tải lên.");
        }

        String filename = "upload-" + UUID.randomUUID() + "." + extension;
        Path destination = root.resolve(filename).normalize();
        try {
            if (!destination.startsWith(root)) throw new IllegalArgumentException("Tên tệp ảnh không hợp lệ.");
            Files.createDirectories(root);
            try (var input = file.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
            }
            return filename;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Không lưu được ảnh. Hãy thử lại.");
        }
    }

    public void delete(String filename) {
        if (filename == null || !filename.matches("upload-[0-9a-fA-F-]{36}\\.(jpg|png)")) return;
        try {
            Files.deleteIfExists(root.resolve(filename).normalize());
        } catch (IOException ignored) {
            // A failed cleanup leaves only an unreferenced image file.
        }
    }

    public Path getRoot() {
        return root;
    }
}
