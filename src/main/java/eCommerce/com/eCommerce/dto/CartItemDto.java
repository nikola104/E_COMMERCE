package eCommerce.com.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private String productName;
    private byte[] productImage;
    private double productPrice;
    private Long quantity;
    private double totalPrice;

}
