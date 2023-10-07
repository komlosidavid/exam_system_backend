
package inf.unideb.hu.exam.system.security.token;

import inf.unideb.hu.exam.system.models.Token;
import inf.unideb.hu.exam.system.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for {@link Token} entity.
 */
@Service
public class TokenService {

    /**
     * Secret key.
     */
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    /**
     * Access token expiration time in milliseconds.
     */
    @Value("${application.security.jwt.expiration}")
    private long accessExpiration;

    /**
     * Refresh token expiration time in milliseconds.
     */
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    /**
     * Function to generate a JWT token.
     * @param userDetails of the {@link UserDetails}.
     * @return a new {@link Token}.
     */
    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(new HashMap<>(), userDetails);
    }

    /**
     * Function to generate a JWT Token with {@link Claims}.
     * @param claims for the token body.
     * @param userDetails of the {@link UserDetails}.
     * @return a new {@link Token}.
     */
    public String generateJwtToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        return buildToken(claims, userDetails, accessExpiration);
    }

    /**
     * Function to check if a token is valid.
     * @param token the token.
     * @param userDetails of the {@link UserDetails}.
     * @return boolean of the token validity.
     */
    public boolean isJwtTokenValid(
            String token, UserDetails userDetails) {
        final String username =
                extractUsername(token);
        return username.equals(
                userDetails.getUsername()) &&
                !isJwtTokenExpired(token);
    }

    /**
     * Function to check if a token is expired.
     * @param token the token.
     * @return a boolean of the token expiration.
     */
    private boolean isJwtTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Function to get the expiration date from the tokn.
     * @param token the token.
     * @return a {@link Date} as the expiration.
     */
    private Date extractExpiration(String token) {
        return extractClaimFromJwtToken(token, Claims::getExpiration);
    }

    /**
     * Function to get the {@link User} username from the token.
     * @param token the token.
     * @return a string as the {@link User}'s username.
     */
    public String extractUsername(String token) {
        return extractClaimFromJwtToken(token, Claims::getSubject);
    }

    /**
     * Function to get the a {@link Claims} from the token.
     * @param token the token.
     * @param claimsResolver for the {@link Claims}.
     * @return a generic type of {@link Claims}.
     * @param <T> for handling generic {@link Claims}.
     */
    public <T> T extractClaimFromJwtToken(
            String token,
            Function<Claims, T> claimsResolver) {
        final  Claims claims = extractClaimsFromJwtToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Function to get the {@link Claims} from the token.
     * @param token the token.
     * @return a {@link Claims} from the token.
     */
    private Claims extractClaimsFromJwtToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Function to check if the token is valid.
     * @param token the token.
     * @param userDetails of the {@link UserDetails}.
     * @return a boolean of the token validity.
     */
    public boolean isTokenValid(String token,
                                UserDetails userDetails) {
        final String username =
                extractUsername(token);
        return (username.equals(
                userDetails.getUsername())) &&
                !isTokenExpired(token);
    }

    /**
     * Function to check if the token is expired.
     * @param token the token.
     * @return a boolean of the token expiration.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Function to build a {@link Token}.
     * @param claims as the token body.
     * @param userDetails of the {@link UserDetails}.
     * @param expiration date of the token.
     * @return a new token.
     */
    private String buildToken(Map<String, Object> claims,
                              UserDetails userDetails,
                              long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails
                        .getUsername())
                .setIssuedAt(
                        new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Function to generate a refresh token.
     * @param userDetails of the {@link UserDetails}.
     * @return a refresh token.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(),
                userDetails, refreshExpiration);
    }

    /**
     * Function to get the signing key.
     * @return the signing key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
