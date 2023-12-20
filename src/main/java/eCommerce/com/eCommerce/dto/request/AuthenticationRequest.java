package eCommerce.com.eCommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Email(message = "Invalid Email")
    @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String email;
    @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String password;
}
