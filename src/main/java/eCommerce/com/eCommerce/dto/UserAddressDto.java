package eCommerce.com.eCommerce.dto;

import eCommerce.com.eCommerce.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressDto {

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String postcode;

    private String country;

    private String phoneNumber;

    UserInfoDto userInfo;
}
