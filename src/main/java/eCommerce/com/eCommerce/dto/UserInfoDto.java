package eCommerce.com.eCommerce.dto;

import eCommerce.com.eCommerce.enums.Role;
import eCommerce.com.eCommerce.model.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
