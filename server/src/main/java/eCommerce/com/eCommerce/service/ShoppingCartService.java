package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.response.ShoppingCartResponse;
import eCommerce.com.eCommerce.model.ShoppingCart;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    void createShoppingCart(Long userId);
    void saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUserId(Long shoppingCartId);

    ShoppingCartResponse getCart(Authentication authentication);



}
