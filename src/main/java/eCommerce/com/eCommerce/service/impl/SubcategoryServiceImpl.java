package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.request.SubcategoryRequest;
import eCommerce.com.eCommerce.exception.CategoryNotFoundException;
import eCommerce.com.eCommerce.exception.SubcategoryNotFoundException;
import eCommerce.com.eCommerce.model.Category;
import eCommerce.com.eCommerce.model.Subcategory;
import eCommerce.com.eCommerce.repository.CategoryRepository;
import eCommerce.com.eCommerce.repository.SubcategoryRepository;
import eCommerce.com.eCommerce.service.CategoryService;
import eCommerce.com.eCommerce.service.SubcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryService categoryService;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryService categoryService) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryService = categoryService;
    }

    @Override
    public String saveSubcategory(SubcategoryRequest request) {
        var category = categoryService.getCategory(request.getCategoryId());

        var subcategory = Subcategory.builder()
                .name(request.getName())
                .category(category)
                .build();
        subcategoryRepository.save(subcategory);
        return "Successfully saved subcategory!";

    }

    @Override
    public Subcategory getSubcategory(Long id) {
        var subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found!"));
        return subcategory;
    }

    @Override
    public String updateSubcategory(Long id, SubcategoryRequest request) {
        var subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found!"));

        subcategory.setName(request.getName());
        subcategoryRepository.save(subcategory);
        return "Successfully updated subcategory!";


    }

    @Override
    public String deleteSubcategory(Long id) {
        var subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found!"));
        subcategoryRepository.delete(subcategory);
        return "Successfully deleted subcategory!";
    }

    @Override
    public List<Subcategory> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        if(subcategories.isEmpty()){
            throw new SubcategoryNotFoundException("No subcategories found!");
        }
        return subcategories;
    }

    @Override
    public Subcategory getCategory(Long id) {
        var subcategory = subcategoryRepository.findById(id)
                .orElseThrow(()-> new SubcategoryNotFoundException("Subcategory not found!"));
        return subcategory;
    }
}
