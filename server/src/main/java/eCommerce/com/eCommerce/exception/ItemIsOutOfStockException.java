package eCommerce.com.eCommerce.exception;

public class ItemIsOutOfStockException extends RuntimeException{
    public ItemIsOutOfStockException(String message) {
        super(message);
    }
}
