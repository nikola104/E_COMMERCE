package eCommerce.com.eCommerce.auth;

import eCommerce.com.eCommerce.dto.request.AuthenticationRequest;
import eCommerce.com.eCommerce.dto.request.RegistrationRequest;
import eCommerce.com.eCommerce.dto.response.AuthenticationResponse;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.service.UserService;
import eCommerce.com.eCommerce.service.impl.JWTServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final JWTServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, JWTServiceImpl jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegistrationRequest request) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .isVerified(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            userService.save(user);

            return "Successfully registration!";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if(!(request.getPassword().equals(request.getConfirmPassword()))){
            //password does not match
            throw new BadCredentialsException("Password and confirm password must be the same");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userService.findByEmail(request.getEmail());

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();

        }catch (BadCredentialsException e){
            //da hvurlq cutom exception
            throw new BadCredentialsException("Invalid username or password");
        }

    }
}
