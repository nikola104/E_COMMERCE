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
public class TypeRequest {
    @NotBlank(message = "Invalid name: Empty  name")
    @NotNull(message = "Invalid name:  Name is NULL")
    private String name;
    @NotNull(message = "Invalid subcategory id :  subcategory id is NULL")
    private Long subcategoryId;
}
