package eCommerce.com.eCommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse {
    private double totalPrice;
    private Long totalItems;

}
