package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.dto.ProductDto;
import eCommerce.com.eCommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/save-product")
    public ResponseEntity<String> saveProduct(@RequestPart("data") @Valid ProductRequest productRequest,
                                              @RequestPart("image") MultipartFile image) throws IOException {

        return new ResponseEntity<>(productService.saveProduct(productRequest, image), HttpStatus.CREATED);

    }
    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }



}
