package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.CartItemRequest;

public interface CartItemService {
    String saveItemToCart(CartItemRequest cartItemRequest);
}
