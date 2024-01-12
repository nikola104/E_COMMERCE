package eCommerce.com.eCommerce.dto.request;

import eCommerce.com.eCommerce.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Invalid Product Name: Empty Product Name")
    @NotNull(message = "Invalid Product Name: Product Name is NULL")
    private String productName;
    @NotBlank(message = "Invalid description: Empty description")
    @NotNull(message = "Invalid description: description is NULL")
    private String description;
    @NotBlank(message = "Invalid color: Empty color")
    @NotNull(message = "Invalid color: color is NULL")
    private String color;
    @NotNull(message = "Invalid quantity: quantity is NULL")
    private Long quantity;
    @NotBlank(message = "Invalid material: Empty material")
    @NotNull(message = "Invalid material: material is NULL")
    private String material;
    private Long subcategoryId;
    private Long typeId;
}
