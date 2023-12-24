package eCommerce.com.eCommerce.dto.request;

import eCommerce.com.eCommerce.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentRequest{
    @NotBlank(message = "Invalid cardName is Empty")
    @NotNull(message = "Invalid cardName  is NULL")
    private String cardName;
    @NotBlank(message = "Invalid cardNumber is Empty")
    @NotNull(message = "Invalid cardNumber  is NULL")
    //@Pattern(regexp = "^(\\d{4}[- ]?){3}\\d{4}$", message = "Invalid credit card number")
    private String cardNumber;
    @NotBlank(message = "Invalid expiryMonth is Empty")
    @NotNull(message = "Invalid expiryMonth  is NULL")
    @Pattern(regexp = "^(0[1-9]|1[0-2])", message = "Invalid Expiry Month")
    private String expiryMonth;
    @NotBlank(message = "Invalid expiryYear is Empty")
    @NotNull(message = "Invalid expiryYear  is NULL")
    @Pattern(regexp = "^\\d{2}$", message = "Invalid Expiry Year")
    private String expiryYear;
    @NotBlank(message = "Invalid holderName is Empty")
    @NotNull(message = "Invalid holderName  is NULL")
    private String holderName;

    @NotNull(message = "Invalid userId is NULL")
    private Long userId;
}
