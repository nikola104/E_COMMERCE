package eCommerce.com.eCommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemInTheCartRequest {
    @NotNull(message = "productId is required")
    private Long productId;
    @Min(value = 0, message = "Quantity must be a positive number")
    private Long quantity;
}
