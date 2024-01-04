package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.SubcategoryRequest;
import eCommerce.com.eCommerce.model.Subcategory;

public interface SubcategoryService {
    String saveSubcategory(SubcategoryRequest request);

    Subcategory getSubcategory(Long id);

    String updateSubcategory(Long id, SubcategoryRequest request);

    String deleteSubcategory(Long id);
}
