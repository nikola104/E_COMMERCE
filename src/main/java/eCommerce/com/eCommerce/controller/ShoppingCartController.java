package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.response.ShoppingCartResponse;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-cart")
public class ShoppingCartController {
        private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/get-cart/{userId}")
    public ResponseEntity<ShoppingCartResponse> getCart(@PathVariable Long userId){
        return new ResponseEntity<>(shoppingCartService.getCart(userId), HttpStatus.OK);
    }
}
