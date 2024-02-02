package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;
import eCommerce.com.eCommerce.exception.ItemIsOutOfStockException;
import eCommerce.com.eCommerce.model.CartItem;
import eCommerce.com.eCommerce.model.Product;
import eCommerce.com.eCommerce.model.ShoppingCart;
import eCommerce.com.eCommerce.repository.CartItemRepository;
import eCommerce.com.eCommerce.service.CartItemService;
import eCommerce.com.eCommerce.service.ProductService;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final static Logger LOGGER = LoggerFactory.
            getLogger(ProductService.class);

    //todo: to fix the bug with removing only the current item

    public CartItemServiceImpl(ShoppingCartService shoppingCartService, ProductService productService, CartItemRepository cartItemRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public String saveItemToCart(CartItemRequest cartItemRequest) {
        var product = productService.findProductById(cartItemRequest.getProductId());
        var shoppingCart = shoppingCartService.findShoppingCartByUserId(cartItemRequest.getUserId());

        //checking if the product is out of stock and if a customer can buy it
        if(product.getQuantity() < cartItemRequest.getQuantity() && cartItemRequest.getQuantity() >= 2){
            throw new ItemIsOutOfStockException("Item is out of stock. Please consider trying with a lower quantity.");
        }
        if(product.getQuantity() < cartItemRequest.getQuantity() && cartItemRequest.getQuantity() == 1 ){
            throw new ItemIsOutOfStockException("Item is out of stock");
        }

        //updating the quantity of the product temporarily
        product.setQuantity(product.getQuantity() - cartItemRequest.getQuantity());

        //checking the quantity status of the product
        String quantityStatus = null;

        if(product.getQuantity() <= 10){
            quantityStatus = "RUNNING LOW - Less than 10 available";
        }
        if(product.getQuantity() <= 20 && product.getQuantity() > 10){
            quantityStatus = "HURRY UP - Selling out fast!";
        }
        product.setQuantityStatus(quantityStatus);

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

    @Override
    public String removeItemFromCart(RemoveItemFromCartRequest request) {
        var shoppingCart = shoppingCartService.findShoppingCartByUserId(request.getUserId());
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCartId(shoppingCart.getId());

        for(CartItem cartItem : cartItems){{
            if(cartItem.getProduct().getId() == request.getProductId()){
                LOGGER.info(cartItem.getProduct().getId()+" ?=? "+ request.getProductId());
                updateShoppingCart(shoppingCart,cartItem);
                returnQuantityOfTheStock(cartItem,request.getProductId());
                cartItemRepository.delete(cartItem);
            }
        }}
        return "Item removed from cart";
    }
    void updateShoppingCart(ShoppingCart cart, CartItem cartItem){
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getPrice());
        shoppingCartService.saveShoppingCart(cart);
    }
    void returnQuantityOfTheStock(CartItem cartItem, Long productId){
        var product = productService.findProductById(productId);
        product.setQuantity(product.getQuantity()+cartItem.getQuantity());

        //updating the quantity status of the product
        String quantityStatus = null;
        if(product.getQuantity() <= 10){
            quantityStatus = "RUNNING LOW - Less than 10 available";
        }
        if(product.getQuantity() <= 20 && product.getQuantity() > 10){
            quantityStatus = "HURRY UP - Selling out fast!";
        }
        product.setQuantityStatus(quantityStatus);
        productService.saveProduct(product);


    }
}
