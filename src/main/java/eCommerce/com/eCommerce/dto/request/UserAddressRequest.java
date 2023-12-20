package eCommerce.com.eCommerce.dto.request;

import eCommerce.com.eCommerce.model.User;
import jakarta.persistence.*;
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
public class UserAddressRequest {

    @NotBlank(message = "Invalid addressLine1 is Empty")
    @NotNull(message = "Invalid addressLine1  is NULL")
    private String addressLine1;
    @NotBlank(message = "Invalid addressLine2 is Empty")
    @NotNull(message = "Invalid addressLine2  is NULL")
    private String addressLine2;
    @NotBlank(message = "Invalid city is Empty")
    @NotNull(message = "Invalid city  is NULL")
    private String city;
    @NotBlank(message = "Invalid postcode is Empty")
    @NotNull(message = "Invalid postcode  is NULL")
    @Pattern(regexp = "\\d{4}", message = "Invalid postcode")
    private String postcode;
    @NotBlank(message = "Invalid country is Empty")
    @NotNull(message = "Invalid country  is NULL")
    private String country;
    @NotBlank(message = "Invalid phoneNumber is Empty")
    @NotNull(message = "Invalid phoneNumber  is NULL")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    @NotNull(message = "Invalid user is NULL")
    private User user;
}
