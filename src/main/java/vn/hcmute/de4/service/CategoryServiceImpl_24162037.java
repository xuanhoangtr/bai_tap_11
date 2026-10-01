package vn.hcmute.de4.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import vn.hcmute.de4.model.Category_24162037;
import vn.hcmute.de4.repository.CategoryRepository_24162037;

@Service
public class CategoryServiceImpl_24162037 implements CategoryService_24162037 {
    private final CategoryRepository_24162037 repository;
    public CategoryServiceImpl_24162037(CategoryRepository_24162037 repository) { this.repository = repository; }
    @Override public List<Category_24162037> findAllWithVideoCount() { return repository.findAllWithVideoCount(); }
    @Override public Optional<Category_24162037> findById(Long id) { return repository.findById(id); }
}
