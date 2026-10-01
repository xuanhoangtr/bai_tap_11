package vn.hcmute.de4.service;

import java.util.List;
import java.util.Optional;
import vn.hcmute.de4.model.Category_24162037;

public interface CategoryService_24162037 {
    List<Category_24162037> findAllWithVideoCount();
    Optional<Category_24162037> findById(Long id);
}
