package vn.hcmute.de4.repository;

import java.util.List;
import java.util.Optional;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Product_24162037;

public interface ProductRepository_24162037 {
    PageResult_24162037<Product_24162037> findActivePage(int page, int size);
    PageResult_24162037<Product_24162037> findAdminPage(int page, int size);
    Optional<Product_24162037> findActiveById(Long id);
    Optional<Product_24162037> findAdminById(Long id);
    Product_24162037 insert(Product_24162037 product);
    void update(Product_24162037 product);
    void deactivate(Long id);
    Optional<Product_24162037> lockActiveById(Long id);
    List<Product_24162037> lockActiveByIds(List<Long> ids);
    boolean decreaseStock(Long id, int quantity);
}
