package eCommerce.com.eCommerce.exception;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
