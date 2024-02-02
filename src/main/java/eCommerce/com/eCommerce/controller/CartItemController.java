package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cart-item")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    @PostMapping("/save-item-to-cart")
    public ResponseEntity<String> saveItemToCart(@RequestBody @Valid CartItemRequest cartItemRequest){
        return new ResponseEntity<>(cartItemService.saveItemToCart(cartItemRequest), HttpStatus.CREATED);

    }

}
