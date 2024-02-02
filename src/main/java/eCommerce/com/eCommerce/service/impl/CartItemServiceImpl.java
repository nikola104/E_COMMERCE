package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.exception.ItemIsOutOfStockException;
import eCommerce.com.eCommerce.model.CartItem;
import eCommerce.com.eCommerce.repository.CartItemRepository;
import eCommerce.com.eCommerce.service.CartItemService;
import eCommerce.com.eCommerce.service.ProductService;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(ShoppingCartService shoppingCartService, ProductService productService, CartItemRepository cartItemRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public String saveItemToCart(CartItemRequest cartItemRequest) {
        var product = productService.findProductById(cartItemRequest.getProductId());
        var shoppingCart = shoppingCartService.findShoppingCartByUserId(cartItemRequest.getUserId());

        if(product.getQuantity() < cartItemRequest.getQuantity() && cartItemRequest.getQuantity() >= 2){
            throw new ItemIsOutOfStockException("Item is out of stock. Please consider trying with a lower quantity.");
        }
        if(product.getQuantity() < cartItemRequest.getQuantity() && cartItemRequest.getQuantity() == 1 ){
            throw new ItemIsOutOfStockException("Item is out of stock");
        }
        //updating the quantity of the product temporarily
        product.setQuantity(product.getQuantity() - cartItemRequest.getQuantity());
        productService.saveProduct(product);

        //creating a new cart item
        var cartItem = CartItem.builder()
                .product(product)
                .shoppingCart(shoppingCart)
                .quantity(cartItemRequest.getQuantity())
                .price(product.getPrice() * cartItemRequest.getQuantity())
                .build();

        //saving the cart item
        cartItemRepository.save(cartItem);

        //summing the total price of the shopping cart
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + cartItem.getPrice());
        //update the shopping cart
        shoppingCartService.saveShoppingCart(shoppingCart);

        return "Item added to cart";
        

    }
}
