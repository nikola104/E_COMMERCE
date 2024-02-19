package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.CartItemDto;
import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;
import eCommerce.com.eCommerce.dto.request.UpdateItemInTheCartRequest;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface CartItemService {
    String saveItemToCart(CartItemRequest cartItemRequest, Authentication authentication);

    String removeItemFromCart(RemoveItemFromCartRequest request, Authentication authentication);

    List<CartItemDto> getCartItemsByCartId(Authentication authentication);

    String updateQuantityInTheCart(UpdateItemInTheCartRequest request, Authentication authentication);
}
