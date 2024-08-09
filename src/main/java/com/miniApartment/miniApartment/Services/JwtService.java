package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.TokenEntity;
import com.miniApartment.miniApartment.Repository.TokenRepository;
import com.miniApartment.miniApartment.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public static final String SECRET_REFRESH_KEY = "192HHB12UI3H1IU23BI382U4892883929831903801BI12UB3IU1B23IIUB123";
    @Autowired
    private TokenService tokenService;

    public String generateToken(String userName) {
        String role = tokenService.getRoleByEmail(userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userName);
    }

    public String generateRefrershToken(String userName) {
        String role = tokenService.getRoleByEmail(userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String refreshToken = createRefreshToken(claims, userName);
        tokenService.updateToken(refreshToken, userName);
        return refreshToken;
    }

    public String generateNewRefrershToken(String userName) {
        String role = tokenService.getRoleByEmail(userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String refreshToken = createNewRefreshToken(claims, userName);
        tokenService.updateToken(refreshToken, userName);
        return refreshToken;
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*100))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(tokenService.getExpiredTimeToken(userName))
                .signWith(getSignKeyRefresh(), SignatureAlgorithm.HS256).compact();
    }

    private String createNewRefreshToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .signWith(getSignKeyRefresh(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_REFRESH_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public int checkTokenExprired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // check expired token
            boolean isTokenExpired = claims.getExpiration().before(new Date());
            if (isTokenExpired) return 1;
            return 0;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage());
            return 2;
        }
    }


    public int validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKeyRefresh())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // check expired token
            boolean isTokenExpired = claims.getExpiration().before(new Date());
            if (isTokenExpired) return 1;
            // check token in db
            String email = claims.getSubject();
            if (!tokenService.checkTokenInDB(token, email)) return 2;
            return 0;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage());
            return 2;
        }
    }

    public TokenDTO generateTokenByRefreshtoken(String refreshtoken) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(getSignKeyRefresh())
                .build()
                .parseClaimsJws(refreshtoken)
                .getBody();
        String userName = claim.getSubject();
        String refreshToken = generateRefrershToken(userName);
        String accessToken = generateToken(userName);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRefreshToken(refreshToken);
        tokenDTO.setAccessToken(accessToken);
        return tokenDTO;
    }

    public void deleteToken(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userName = claim.getSubject();
        tokenService.deleteToken(userName);
    }

}
