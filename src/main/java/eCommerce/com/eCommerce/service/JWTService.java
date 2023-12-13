package eCommerce.com.eCommerce.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface JWTService {
    public String generateToken(UserDetails userDetails);
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
    public String generateRefreshToken(UserDetails userDetails);
    public String extractEmail(String token);
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver);
    public boolean isTokenValid(String token, UserDetails userDetails);

}
