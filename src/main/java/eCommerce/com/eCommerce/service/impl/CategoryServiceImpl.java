package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.request.CategoryRequest;
import eCommerce.com.eCommerce.exception.CategoryNotFoundException;
import eCommerce.com.eCommerce.model.Category;
import eCommerce.com.eCommerce.repository.CategoryRepository;
import eCommerce.com.eCommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String saveCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();

        categoryRepository.save(category);

        return "Category saved successfully";
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new CategoryNotFoundException("No categories found");
        }
        return categories;
    }
}
