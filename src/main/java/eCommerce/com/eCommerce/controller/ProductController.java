package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/save-product")
    public ResponseEntity<String> saveProduct(@RequestBody @Valid ProductRequest productRequest,
                                              @RequestParam("image")MultipartFile image){

return null;

    }



}
