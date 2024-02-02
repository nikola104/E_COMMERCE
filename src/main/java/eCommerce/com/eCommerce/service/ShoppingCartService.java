package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.model.ShoppingCart;

public interface ShoppingCartService {
    void createShoppingCart(Long userId);
    void saveShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUserId(Long shoppingCartId);
}
