package eCommerce.com.eCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveItemFromCartRequest {
    @NotNull(message = "User id is required")
    private Long userId;
    @NotNull(message = "Product id is required")
    private Long productId;
}
