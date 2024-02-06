package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.CartItemDto;
import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;
import eCommerce.com.eCommerce.exception.CartItemNotFoundException;
import eCommerce.com.eCommerce.exception.ItemIsOutOfStockException;
import eCommerce.com.eCommerce.exception.ShoppingCartNotFoundException;
import eCommerce.com.eCommerce.model.CartItem;
import eCommerce.com.eCommerce.model.Product;
import eCommerce.com.eCommerce.model.ShoppingCart;
import eCommerce.com.eCommerce.repository.CartItemRepository;
import eCommerce.com.eCommerce.service.CartItemService;
import eCommerce.com.eCommerce.service.ProductService;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final static Logger LOGGER = LoggerFactory.
            getLogger(ProductService.class);



    public CartItemServiceImpl(ShoppingCartService shoppingCartService, ProductService productService, CartItemRepository cartItemRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public String saveItemToCart(CartItemRequest cartItemRequest) {
        var product = productService.findProductById(cartItemRequest.getProductId());
        var shoppingCart = shoppingCartService.findShoppingCartByUserId(cartItemRequest.getUserId());
        var cartItems = cartItemRepository.findAllByShoppingCartId(shoppingCart.getId());

        //checking if the product is already in the cart
        if(updateQuantityIfDuplicateItem(cartItems, product, cartItemRequest,shoppingCart)){
            return "Item is already in the cart. Quantity updated.";
        }



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
                .addedAt(LocalDateTime.now())
                .build();

        //saving the cart item
        cartItemRepository.save(cartItem);

        //summing the total price of the shopping cart
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + cartItem.getPrice());
        //update the shopping cart total quantity
        shoppingCart.setTotalItems(shoppingCart.getTotalItems() + cartItemRequest.getQuantity());
        //update the shopping cart
        shoppingCartService.saveShoppingCart(shoppingCart);

        return "Item added to cart";
        

    }

    private Boolean updateQuantityIfDuplicateItem(List<CartItem> cartItems, Product product, CartItemRequest cartItemRequest, ShoppingCart shoppingCart) {

        Boolean isDuplicate = false;

        for (CartItem item : cartItems) {
            if (item.getProduct().getSku().equals(product.getSku())) {
                isDuplicate = true;
                item.setQuantity(item.getQuantity() + cartItemRequest.getQuantity());
                item.setPrice(item.getPrice() + (product.getPrice() * cartItemRequest.getQuantity()));

                //updating the quantity of the product temporarily
                product.setQuantity(product.getQuantity() - cartItemRequest.getQuantity());

                //updating the quantity status of the product
                updateQuantityStatusOfTheProduct(product);
                //saving the product to the shopping cart
                cartItemRepository.save(item);

                //summing the total price of the shopping cart
                shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + (product.getPrice() * cartItemRequest.getQuantity()));
                //update the shopping cart total quantity
                shoppingCart.setTotalItems(shoppingCart.getTotalItems() + cartItemRequest.getQuantity());
                shoppingCartService.saveShoppingCart(shoppingCart);

            }
        }
        return isDuplicate;

    }

    private void updateQuantityStatusOfTheProduct(Product product) {
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

    @Override
    public String removeItemFromCart(RemoveItemFromCartRequest request) {
        var shoppingCart = shoppingCartService.findShoppingCartByUserId(request.getUserId());
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCartId(shoppingCart.getId());
        if(cartItems.isEmpty()){
            throw new CartItemNotFoundException("Your cart is empty!");
        }
            boolean isItemInCart = false;
        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct().getId() == request.getProductId()) {
                updateShoppingCart(shoppingCart, cartItem);
                returnQuantityOfTheStock(cartItem, request.getProductId());
                cartItemRepository.delete(cartItem);
                isItemInCart = true;
            }
        }
        if(!isItemInCart){
            throw new CartItemNotFoundException("Item is not in the cart");
        }
        return "Item removed from cart";
    }

    @Override
    public List<CartItemDto> getCartItemsByCartId(Long userId) {
        var shoppingCartId = shoppingCartService.findShoppingCartByUserId(userId).getId();
        var shoppingCart = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
        if(shoppingCart.isEmpty()){
            throw new ShoppingCartNotFoundException("Your cart is empty!");
        }
       return shoppingCart.stream()
               .map(cartItem -> CartItemDto.builder()
                       .productName(cartItem.getProduct().getProductName())
                       .quantity(cartItem.getQuantity())
                       .productImage(cartItem.getProduct().getImageData())
                       .totalPrice(cartItem.getPrice())
                       .productPrice(cartItem.getProduct().getPrice())
                          .build())
               .collect(Collectors.toList());




    }

    void updateShoppingCart(ShoppingCart cart, CartItem cartItem){
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getPrice());
        cart.setTotalItems(cart.getTotalItems()-cartItem.getQuantity());
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
    // Method to remove expired items from the cart
/*    @Scheduled(cron = "0 0 0 * * *") // Cron expression for midnight execution
    public void checkIfItPassed7Days(){
        List<CartItem> cartItems = cartItemRepository.findAll();
        for(CartItem cartItem : cartItems){
            if(cartItem.getAddedAt().plusDays(7).isBefore(LocalDateTime.now())){
                updateShoppingCart(cartItem.getShoppingCart(),cartItem);
                returnQuantityOfTheStock(cartItem, cartItem.getProduct().getId());
                cartItemRepository.delete(cartItem);
            }
        }

    }*/
    @Scheduled(cron = "0 0 0 * * *") // Cron expression for midnight execution
    public void checkIfItPassed7Days(){
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        List<CartItem> cartItems = cartItemRepository.findItemsCreatedSevenDaysBefore(cutoffDate);
        for(CartItem cartItem : cartItems){
            updateShoppingCart(cartItem.getShoppingCart(),cartItem);
            returnQuantityOfTheStock(cartItem, cartItem.getProduct().getId());
            cartItemRepository.delete(cartItem);
        }

    }

}
