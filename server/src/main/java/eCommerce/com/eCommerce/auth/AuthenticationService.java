package eCommerce.com.eCommerce.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import eCommerce.com.eCommerce.dto.request.AuthenticationRequest;
import eCommerce.com.eCommerce.dto.request.RegistrationRequest;
import eCommerce.com.eCommerce.dto.response.AuthenticationResponse;
import eCommerce.com.eCommerce.exception.InvalidCredentialsException;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.service.ShoppingCartService;
import eCommerce.com.eCommerce.service.UserService;
import eCommerce.com.eCommerce.service.impl.CartItemServiceImpl;
import eCommerce.com.eCommerce.service.impl.JWTServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final JWTServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;
    private final static Logger LOGGER = LoggerFactory.
            getLogger(AuthenticationService.class);

    public AuthenticationService(UserService userService, JWTServiceImpl jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.shoppingCartService = shoppingCartService;
    }

    public String register(RegistrationRequest request) {
        //check if email exists
        userService.existsByEmail(request.getEmail());


        //password does not match
        if(!(request.getPassword().equals(request.getConfirmPassword()))){
            LOGGER.info("Password does not match!");
            throw new InvalidCredentialsException("Password does not match!");

        }



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

            //creating a shopping cart for the user
            shoppingCartService.createShoppingCart(user.getId());


            return "Successfully registration!";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
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

            throw new InvalidCredentialsException("Invalid email or password");
        }

    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if(userEmail != null){
            var user = userService.findByEmail(userEmail);
            if(jwtService.isTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }
}
