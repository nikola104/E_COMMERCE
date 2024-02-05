package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.CartItemDto;
import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;

import java.util.List;

public interface CartItemService {
    String saveItemToCart(CartItemRequest cartItemRequest);

    String removeItemFromCart(RemoveItemFromCartRequest request);

    List<CartItemDto> getCartItemsByCartId(Long userID);
}
