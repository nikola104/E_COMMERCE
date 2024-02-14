package eCommerce.com.eCommerce.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
