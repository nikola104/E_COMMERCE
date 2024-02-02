package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;

public interface CartItemService {
    String saveItemToCart(CartItemRequest cartItemRequest);

    String removeItemFromCart(RemoveItemFromCartRequest request);
}
