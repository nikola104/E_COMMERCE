package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.CategoryRequest;
import eCommerce.com.eCommerce.model.Category;

import java.util.List;

public interface CategoryService {
    String saveCategory(CategoryRequest categoryRequest);

    List<Category> getAllCategories();
}
