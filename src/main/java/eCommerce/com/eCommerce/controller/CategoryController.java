package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.CategoryRequest;
import eCommerce.com.eCommerce.model.Category;
import eCommerce.com.eCommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/save-category")
    public ResponseEntity<String> saveCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.saveCategory(categoryRequest), HttpStatus.CREATED);
    }
    @GetMapping("/get-all-categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }


}
