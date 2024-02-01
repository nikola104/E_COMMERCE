package eCommerce.com.eCommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityRequest {
    @NotNull(message = "Invalid quantity: quantity is NULL")
    @NotBlank(message = "Invalid quantity: You have to input quantity")
   private Integer quantity;
}
