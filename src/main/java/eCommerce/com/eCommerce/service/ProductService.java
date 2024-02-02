package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.ProductDto;
import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.dto.request.UpdateQuantityRequest;
import eCommerce.com.eCommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    String saveProduct(ProductRequest productRequest, MultipartFile image) throws IOException;

    ProductDto getProductById(Long id);
    Product findProductById(Long id);
 // za sq ne   void updateProductQuantityAfterCartAddition(Long productId, int quantity);

    String updateProductQuantity(Long id, UpdateQuantityRequest updateQuantityRequest);
    void saveProduct(Product product);
}
