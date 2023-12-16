package eCommerce.com.eCommerce.dto.request;

import eCommerce.com.eCommerce.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Invalid First Name: Empty First name")
    @NotNull(message = "Invalid Fist Name: First Name is NULL")
    @Size(min = 3, max = 18, message = "Invalid Name: Exceeds 18 characters")
    private String firstName;
    @NotBlank(message = "Invalid Last Name: Empty Last name")
    @NotNull(message = "Invalid Last Name: Last Name is NULL")
    @Size(min = 3, max = 18, message = "Invalid Name: Exceeds 18 characters")
    private String lastName;
    @Email(message = "Invalid Email")
    private String email;
   // @Size(min = 8, message = "The password must be at least 8 characters long!")
    @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String password;
   // @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String confirmPassword;
    private Role role;

}
