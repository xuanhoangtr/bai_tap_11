package vn.hcmute.de4.service;

import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Product_24162037;
import vn.hcmute.de4.repository.ProductRepository_24162037;

@Service
public class ProductService_24162037 {
    private final ProductRepository_24162037 repository;
    private final ProductImageStorage_24162037 imageStorage;
    public ProductService_24162037(ProductRepository_24162037 repository, ProductImageStorage_24162037 imageStorage) { this.repository = repository; this.imageStorage = imageStorage; }
    public PageResult_24162037<Product_24162037> findPage(int page) { return repository.findActivePage(Math.max(0, page), 9); }
    public Optional<Product_24162037> findById(Long id) { return repository.findActiveById(id); }
    public PageResult_24162037<Product_24162037> findAdminPage(int page) { return repository.findAdminPage(Math.max(0, page), 6); }
    public Optional<Product_24162037> findAdminById(Long id) { return repository.findAdminById(id); }
    @Transactional
    public Product_24162037 saveAdmin(Product_24162037 product, boolean newProduct, MultipartFile imageFile) {
        validate(product);
        product.setProductCode(product.getProductCode().trim());
        product.setProductName(product.getProductName().trim());
        product.setDescription(blankToNull(product.getDescription()));
        product.setCategoryName(blankToNull(product.getCategoryName()));
        Product_24162037 existing = null;
        if (!newProduct) {
            if (product.getProductId() == null) throw new IllegalArgumentException("Không tìm thấy sản phẩm.");
            existing = repository.findAdminById(product.getProductId()).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm."));
        } else {
            product.setProductId(null);
        }
        String uploadedImage = imageStorage.save(imageFile);
        product.setImage(uploadedImage != null ? uploadedImage : existing == null ? null : existing.getImage());
        try {
            if (newProduct) {
                return repository.insert(product);
            }
            repository.update(product);
            return product;
        } catch (DuplicateKeyException ex) {
            imageStorage.delete(uploadedImage);
            throw new IllegalArgumentException("Mã sản phẩm đã được sử dụng.");
        } catch (RuntimeException ex) {
            imageStorage.delete(uploadedImage);
            throw ex;
        }
    }
    @Transactional
    public void deleteFromCatalog(Long id) {
        if (repository.findAdminById(id).isEmpty()) throw new IllegalArgumentException("Không tìm thấy sản phẩm.");
        repository.deactivate(id);
    }
    private void validate(Product_24162037 product) {
        if (product == null || blank(product.getProductCode()) || blank(product.getProductName()) || product.getPrice() == null) throw new IllegalArgumentException("Vui lòng nhập mã, tên và giá sản phẩm.");
        if (product.getProductCode().trim().length() > 40 || product.getProductName().trim().length() > 160) throw new IllegalArgumentException("Mã hoặc tên sản phẩm vượt quá độ dài cho phép.");
        if (product.getDescription() != null && product.getDescription().trim().length() > 1000) throw new IllegalArgumentException("Mô tả không được vượt quá 1000 ký tự.");
        if (product.getCategoryName() != null && product.getCategoryName().trim().length() > 100) throw new IllegalArgumentException("Tên danh mục không được vượt quá 100 ký tự.");
        if (product.getPrice().signum() < 0 || product.getStock() < 0) throw new IllegalArgumentException("Giá và số lượng tồn kho không được âm.");
    }
    private boolean blank(String value) { return value == null || value.isBlank(); }
    private String blankToNull(String value) { return blank(value) ? null : value.trim(); }
}
