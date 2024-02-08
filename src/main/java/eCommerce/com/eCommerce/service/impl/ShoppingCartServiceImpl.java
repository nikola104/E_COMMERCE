package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.response.ShoppingCartResponse;
import eCommerce.com.eCommerce.exception.ShoppingCartNotFoundException;
import eCommerce.com.eCommerce.model.ShoppingCart;
import eCommerce.com.eCommerce.repository.ShoppingCartRepository;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import eCommerce.com.eCommerce.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(UserService userService, ShoppingCartRepository shoppingCartRepository) {
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public void createShoppingCart(Long userId) {
        var user = userService.getUserById(userId);

        var shoppingCart = ShoppingCart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .totalPrice(0)
                .totalItems(0L)
                .build();

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
        if(shoppingCart == null){
            throw new ShoppingCartNotFoundException("Shopping cart not found");
        }
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));
    }

    @Override
    public ShoppingCartResponse getCart(Long userId) {
        var shoppingCart = findShoppingCartByUserId(userId);
        return ShoppingCartResponse.builder()
                .totalItems(shoppingCart.getTotalItems())
                .totalPrice(shoppingCart.getTotalPrice())
                .build();

    }


}
