package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.CartItemDto;
import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;
import eCommerce.com.eCommerce.dto.request.UpdateItemInTheCartRequest;
import eCommerce.com.eCommerce.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart-item")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    @PostMapping("/save-item-to-cart")
    public ResponseEntity<String> saveItemToCart(@RequestBody @Valid CartItemRequest cartItemRequest, Authentication authentication){
        return new ResponseEntity<>(cartItemService.saveItemToCart(cartItemRequest,authentication), HttpStatus.CREATED);
    }
    @DeleteMapping("/remove-item-from-cart")
    public ResponseEntity<String> removeItemFromCart(@RequestBody @Valid RemoveItemFromCartRequest request, Authentication authentication){
        return new ResponseEntity<>(cartItemService.removeItemFromCart(request,authentication), HttpStatus.OK);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItemDto>> getCartItemsByCartId(Authentication authentication){
        return new ResponseEntity<>(cartItemService.getCartItemsByCartId(authentication), HttpStatus.OK);
    }

    @PatchMapping("/update-quantity-in-the-cart")
    public ResponseEntity<String> updateQuantityInTheCart(@RequestBody @Valid UpdateItemInTheCartRequest request, Authentication authentication){
        return new ResponseEntity<>(cartItemService.updateQuantityInTheCart(request,authentication),HttpStatus.OK);
    }
    //todo: to think about secure thee endpoint


}
