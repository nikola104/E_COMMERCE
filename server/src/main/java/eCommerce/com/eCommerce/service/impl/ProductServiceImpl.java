package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.ProductDto;
import eCommerce.com.eCommerce.dto.request.ProductRequest;
import eCommerce.com.eCommerce.dto.request.UpdateQuantityRequest;
import eCommerce.com.eCommerce.exception.ProductNotFoundException;
import eCommerce.com.eCommerce.model.*;
import eCommerce.com.eCommerce.repository.ProductRepository;
import eCommerce.com.eCommerce.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SubcategoryService subcategoryService;
    private final ReviewService reviewService;
    private final TypeService typeService;
    private final ShoppingCartService shoppingCartService;
    private final String FOLDER_PATH = "D:\\java projects\\e_commerce\\server_images";
    private final static Logger LOGGER = LoggerFactory.
            getLogger(ProductService.class);

    public ProductServiceImpl(ProductRepository productRepository, SubcategoryService subcategoryService, ReviewService reviewService, TypeService typeService, ShoppingCartService shoppingCartService) {
        this.productRepository = productRepository;
        this.subcategoryService = subcategoryService;
        this.reviewService = reviewService;
        this.typeService = typeService;
        this.shoppingCartService = shoppingCartService;

    }

    @Override
    public String saveProduct(ProductRequest productRequest, MultipartFile image) throws IOException {
        //if there is no image, the imagePath will be null
        String imagePath = null;
        if(image != null){
            String filePath = FOLDER_PATH + "\\" + image.getOriginalFilename();
            imagePath = filePath;
            image.transferTo(new File(filePath));
        }


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
          var product = saveProductWithType(productRequest, imagePath, sku, type);
          productRepository.save(product);


        }
        if(productRequest.getTypeId() == null && productRequest.getSubcategoryId() != null){
            var subcategory = subcategoryService.getSubcategory(productRequest.getSubcategoryId());
            var product = saveProductWithSubcategory(productRequest, imagePath, sku, subcategory);
            productRepository.save(product);;

        }

        return "Product saved successfully!";

    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        List<Review> productReviews = reviewService.findAllReviewsByProductId(id);
        //incrementing the views of the product
        product.setViews(product.getViews() + 1);

        //checking the quantity status of the product
        String quantityStatus = checkingQuantityStatus(product.getQuantity());


        product.setQuantityStatus(quantityStatus);
        productRepository.save(product);

        var productDto = getProductRightProduct(product, productReviews);
        return productDto;
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public String updateProductQuantity(Long id, UpdateQuantityRequest updateQuantityRequest) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        product.setQuantity(updateQuantityRequest.getQuantity());
        //updating the quantity status of the product
        String quantityStatus = checkingQuantityStatus(updateQuantityRequest.getQuantity());

        product.setQuantityStatus(quantityStatus);
        productRepository.save(product);
        return "Product quantity updated successfully!";
    }

    @Override
    public void saveProduct(Product product) {
        if(product == null){
            throw new ProductNotFoundException("Product is null!");
        }
        productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        //check if product is associated with a cart item
        var cartItem = product.getCartItem();
        if(cartItem != null){
            LOGGER.info("Product is associated with a cart item!");
            throw new ProductNotFoundException("Somebody has item: "+ product.getProductName() +" in his cart! Try to delete it later or after one week!");
        }
        if(cartItem == null && product.getQuantity() > 0) {
            LOGGER.info("Product has quantity greater than 0!");
            throw new ProductNotFoundException("Product has quantity greater than 0! First you need to set the quantity to 0! And then you can delete it!");
        }
         if(cartItem == null && product.getQuantity() == 0){
            productRepository.delete(product);
            LOGGER.info("Product with id: " + id + " deleted successfully!");
            return "Product deleted successfully!";
        }
         return "Product is associated with an order or has quantity greater than 0!";


    }

    // TODO: 2/20/2024 delete it later but first u have to check for register it with type
    /*private ProductDto getProductRightProduct(Product product, List<Review> productReviews) {
        if(product.getType() != null){
            return ProductDto.builder()
                    .name(product.getProductName())
                    .description(product.getDescription())
                    .color(product.getColor())
                    .material(product.getMaterial())

                    .typeName(product.getType().getName())
                    .subcategoryName(product.getSubcategory().getName())
                    .categoryName(product.getSubcategory().getCategory().getName())
                    .reviews(productReviews)
                    .price(product.getPrice())
                    .quantityStatus(product.getQuantityStatus())
                    .rating(product.getRating())
                    .build();
        }
       if(product.getType() == null){
              return ProductDto.builder()
                     .name(product.getProductName())
                     .description(product.getDescription())
                     .color(product.getColor())
                     .material(product.getMaterial())

                     .subcategoryName(product.getSubcategory().getName())
                     .categoryName(product.getSubcategory().getCategory().getName())
                     .reviews(productReviews)
                     .price(product.getPrice())
                     .quantityStatus(product.getQuantityStatus())
                     .rating(product.getRating())
                     .build();
       }
       return new ProductDto();
    }*/
    private ProductDto getProductRightProduct(Product product, List<Review> productReviews) throws IOException {
        String filePath = product.getImagePath();

        ProductDto.ProductDtoBuilder builder = ProductDto.builder()
                .name(product.getProductName())
                .description(product.getDescription())
                .color(product.getColor())
                .material(product.getMaterial())
                .subcategoryName(product.getSubcategory().getName())
                .categoryName(product.getSubcategory().getCategory().getName())
                .reviews(productReviews)
                .price(product.getPrice())
                .quantityStatus(product.getQuantityStatus())
                .rating(product.getRating());

        if (product.getType() != null) {
            builder.typeName(product.getType().getName());
        }
        if(product.getImagePath() != null){
            builder.imageData(Files.readAllBytes(new File(filePath).toPath()));
        }



        return builder.build();
    }


    public String skuGenerator(ProductRequest productRequest){
        String sku = productRequest.getProductName().substring(0, 3) +":"+
                productRequest.getColor().substring(0, 3) +":"
                + productRequest.getMaterial().substring(0, 3)+":"+ UUID.randomUUID().toString();
        return sku;
    }
    public Product saveProductWithType(ProductRequest productRequest, String imagePath, String sku, Type type){

        String quantityStatus = checkingQuantityStatus(productRequest.getQuantity());

        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .color(productRequest.getColor())
                .material(productRequest.getMaterial())
                .price(productRequest.getPrice())
                .views(0L)
                .quantityStatus(quantityStatus)
                .quantity(productRequest.getQuantity())
                .imagePath(imagePath)
                .sku(sku)
                .createdAt(LocalDateTime.now())
                .type(type)
                .build();
    }
    public Product saveProductWithSubcategory(ProductRequest productRequest, String imagePath, String sku, Subcategory subcategory){

        String quantityStatus = checkingQuantityStatus(productRequest.getQuantity());

        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .color(productRequest.getColor())
                .material(productRequest.getMaterial())
                .price(productRequest.getPrice())
                .views(0L)
                .quantityStatus(quantityStatus)
                .quantity(productRequest.getQuantity())
                .imagePath(imagePath)
                .sku(sku)
                .createdAt(LocalDateTime.now())
                .subcategory(subcategory)
                .build();
    }

    private static String checkingQuantityStatus(Long quantity) {
        String quantityStatus = null;

        if(quantity <= 10){
            quantityStatus = "RUNNING LOW - Less than 10 available";
        }
        if(quantity <= 20 && quantity > 10){
            quantityStatus = "HURRY UP - Will Selling out fast!";
        }
        return quantityStatus;
    }
}
