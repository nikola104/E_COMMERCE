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
public class ReviewRequest {

    private String comment;
    @NotNull(message = "Invalid rating is NULL")
    private Double rating;
    @NotNull(message = "Invalid userId is NULL")
    private Long productId;
    @NotNull(message = "Invalid userId is NULL")
    private Long userId;



}
