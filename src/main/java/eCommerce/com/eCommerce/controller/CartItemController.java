package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.CartItemDto;
import eCommerce.com.eCommerce.dto.request.CartItemRequest;
import eCommerce.com.eCommerce.dto.request.RemoveItemFromCartRequest;
import eCommerce.com.eCommerce.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart-item")
public class CartItemController {

    //todo: to add created_at column in database and to reserve a product for 7 days
    //todo: to add update quantity in the cart

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    @PostMapping("/save-item-to-cart")
    public ResponseEntity<String> saveItemToCart(@RequestBody @Valid CartItemRequest cartItemRequest){
        return new ResponseEntity<>(cartItemService.saveItemToCart(cartItemRequest), HttpStatus.CREATED);
    }
    @DeleteMapping("/remove-item-from-cart")
    public ResponseEntity<String> removeItemFromCart(@RequestBody @Valid RemoveItemFromCartRequest request){
        return new ResponseEntity<>(cartItemService.removeItemFromCart(request), HttpStatus.OK);
    }
    @GetMapping("/get-items-by-cart-id/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByCartId(@PathVariable Long userId){
        return new ResponseEntity<>(cartItemService.getCartItemsByCartId(userId), HttpStatus.OK);
    }


}
