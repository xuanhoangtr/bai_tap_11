package vn.hcmute.de4.repository;

import java.util.List;
import java.util.Optional;
import vn.hcmute.de4.model.Category_24162037;

public interface CategoryRepository_24162037 {
    List<Category_24162037> findAllWithVideoCount();
    Optional<Category_24162037> findById(Long id);
}
