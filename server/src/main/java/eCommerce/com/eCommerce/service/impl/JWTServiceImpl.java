package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.service.JWTService;
import eCommerce.com.eCommerce.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    /**
     * @param userDetails- the user details
     * @return the token
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    /**
     * @param extraClaims- the claims to add
     * @param userDetails- the user details to add
     * @return the token
     */
    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,jwtExpiration);
    }
    /**
     * @param userDetails- the user details
     * @return the refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails,refreshExpiration);
    }
    /**
     * @param extractClaims- the claims
     * @param userDetails-  the user details
     * @param expiration- the expiration time in milliseconds
     * @return the token
     */
    private String buildToken(
            Map<String,Object> extractClaims,
            UserDetails userDetails,
            long  expiration){

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() +expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * @return the secret key
     */

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
    /**
     * @param token- the token
     * @return the username of the user
     */
    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * @param token- the token
     * @param claimsResolver - the claims resolver
     * @return extarct the claims
     */

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * @param token- the token
     * @return all claims of the token
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
    /**
     * @param token- the token
     * @param userDetails- the user details
     * @return true if the token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userEmail= extractEmail(token);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    /**
     * @param token- the token
     * @return true if the token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token,claims -> claims.getExpiration()).before(new Date());
    }

}
