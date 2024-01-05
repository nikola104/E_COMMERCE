package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.SubcategoryRequest;
import eCommerce.com.eCommerce.model.Subcategory;
import eCommerce.com.eCommerce.service.SubcategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategory")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @PostMapping("/save-subcategory")
    public ResponseEntity<String> saveSubcategory(@RequestBody @Valid SubcategoryRequest request){
       return new ResponseEntity<>(subcategoryService.saveSubcategory(request), HttpStatus.CREATED);
    }
    @GetMapping("/get-subcategory/{id}")
    public ResponseEntity<Subcategory> getSubcategory(@PathVariable Long id){
        return new ResponseEntity<>(subcategoryService.getSubcategory(id), HttpStatus.OK);
    }
    @PutMapping("/update-subcategory/{id}")
    public ResponseEntity<String> updateSubcategory(@PathVariable Long id, @RequestBody @Valid SubcategoryRequest request){
        return new ResponseEntity<>(subcategoryService.updateSubcategory(id, request), HttpStatus.OK);
    }
    @DeleteMapping("/delete-subcategory/{id}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable Long id){
        return new ResponseEntity<>(subcategoryService.deleteSubcategory(id), HttpStatus.OK);
    }
    @GetMapping("/get-all-subcategories")
    public ResponseEntity<List<Subcategory>> getAllSubcategories(){
        return new ResponseEntity<>(subcategoryService.getAllSubcategories(), HttpStatus.OK);
    }

}
