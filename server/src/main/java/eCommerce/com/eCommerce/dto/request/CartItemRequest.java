package eCommerce.com.eCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequest {
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    private Long quantity;
}
