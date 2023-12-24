package eCommerce.com.eCommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentDto {

    private String cardName;

    private String cardNumber;

    private String expiryMonth;

    private String expiryYear;

    private String holderName;

}
