package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.ProductDto;
import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.exception.ProductNotFoundException;
import eCommerce.com.eCommerce.model.Product;
import eCommerce.com.eCommerce.model.Review;
import eCommerce.com.eCommerce.model.Subcategory;
import eCommerce.com.eCommerce.model.Type;
import eCommerce.com.eCommerce.repository.ProductRepository;
import eCommerce.com.eCommerce.service.ProductService;
import eCommerce.com.eCommerce.service.ReviewService;
import eCommerce.com.eCommerce.service.SubcategoryService;
import eCommerce.com.eCommerce.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SubcategoryService subcategoryService;
    private final ReviewService reviewService;
    private final TypeService typeService;

    public ProductServiceImpl(ProductRepository productRepository, SubcategoryService subcategoryService, ReviewService reviewService, TypeService typeService) {
        this.productRepository = productRepository;
        this.subcategoryService = subcategoryService;
        this.reviewService = reviewService;
        this.typeService = typeService;
    }

    @Override
    public String saveProduct(ProductRequest productRequest, MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();

        if(productRequest.getTypeId() == null && productRequest.getSubcategoryId() == null){
            throw new IllegalStateException("The product must have a type or a subcategory!");
        }

        if(productRequest.getTypeId() != null && productRequest.getSubcategoryId() != null){
            throw new NullPointerException("You must choose a subcategory or a type!");
        }

        //generating sku for a product
        String sku = skuGenerator(productRequest);

        if(productRequest.getTypeId() != null && productRequest.getSubcategoryId() == null){
          var type = typeService.getType(productRequest.getTypeId());
          var product = saveProductWithType(productRequest, imageData, sku, type);
          productRepository.save(product);
        }
        if(productRequest.getTypeId() == null && productRequest.getSubcategoryId() != null){
            var subcategory = subcategoryService.getSubcategory(productRequest.getSubcategoryId());
            var product = saveProductWithSubcategory(productRequest, imageData, sku, subcategory);
            productRepository.save(product);
        }

        return "Product saved successfully!";

    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        List<Review> productReviews = reviewService.findAllReviewsByProductId(id);
        //incrementing the views of the product
        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        var productDto = getProductRightProduct(product, productReviews);
        return productDto;
    }

    private ProductDto getProductRightProduct(Product product, List<Review> productReviews) {
        if(product.getType() != null){
            return ProductDto.builder()
                    .name(product.getProductName())
                    .description(product.getDescription())
                    .color(product.getColor())
                    .material(product.getMaterial())
                    .imageData(product.getImageData())
                    .typeName(product.getType().getName())
                    .subcategoryName(product.getSubcategory().getName())
                    .categoryName(product.getSubcategory().getCategory().getName())
                    .reviews(productReviews)
                    .rating(product.getRating())
                    .build();
        }
       if(product.getType() == null){
              return ProductDto.builder()
                     .name(product.getProductName())
                     .description(product.getDescription())
                     .color(product.getColor())
                     .material(product.getMaterial())
                     .imageData(product.getImageData())
                     .subcategoryName(product.getSubcategory().getName())
                     .categoryName(product.getSubcategory().getCategory().getName())
                     .reviews(productReviews)
                     .rating(product.getRating())
                     .build();
       }
       return new ProductDto();
    }


    public String skuGenerator(ProductRequest productRequest){
        String sku = productRequest.getProductName().substring(0, 3) +":"+
                productRequest.getColor().substring(0, 3) +":"
                + productRequest.getMaterial().substring(0, 3)+":"+ UUID.randomUUID().toString();
        return sku;
    }
    public Product saveProductWithType(ProductRequest productRequest, byte[] imageData, String sku, Type type){
        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .color(productRequest.getColor())
                .material(productRequest.getMaterial())
                .views(0L)
                .imageData(imageData)
                .sku(sku)
                .createdAt(LocalDateTime.now())
                .type(type)
                .build();
    }
    public Product saveProductWithSubcategory(ProductRequest productRequest, byte[] imageData, String sku, Subcategory subcategory){
       return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .color(productRequest.getColor())
                .material(productRequest.getMaterial())
                .views(0L)
                .imageData(imageData)
                .sku(sku)
                .createdAt(LocalDateTime.now())
                .subcategory(subcategory)
                .build();
    }
}
