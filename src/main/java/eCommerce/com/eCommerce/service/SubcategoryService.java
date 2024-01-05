package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.SubcategoryRequest;
import eCommerce.com.eCommerce.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    String saveSubcategory(SubcategoryRequest request);

    Subcategory getSubcategory(Long id);

    String updateSubcategory(Long id, SubcategoryRequest request);

    String deleteSubcategory(Long id);

    List<Subcategory> getAllSubcategories();
    Subcategory getCategory(Long id);
}
