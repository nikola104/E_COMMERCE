package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.ProductDto;
import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.dto.request.UpdateQuantityRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    String saveProduct(ProductRequest productRequest, MultipartFile image) throws IOException;

    ProductDto getProductById(Long id);

    String updateProductQuantity(Long id, UpdateQuantityRequest updateQuantityRequest);
}
