package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.TokenEntity;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.TokenRepository;
import com.miniApartment.miniApartment.Repository.UserRepository;
import com.miniApartment.miniApartment.Services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    public static final String SECRET_REFRESH_KEY = "192HHB12UI3H1IU23BI382U4892883929831903801BI12UB3IU1B23IIUB123";

    public void updateToken(String token, String email) {
        TokenEntity tokenEntity = tokenRepository.getTokenEntitiesByEmail(email);
        if (tokenEntity == null) {
            Claims claims1 = extractRefreshToken(token);
            tokenEntity = new TokenEntity();
            tokenEntity.setEmail(email);
            tokenEntity.setExpriredToken(claims1.getExpiration());
        }
        tokenEntity.setRefreshToken(token);
        tokenRepository.save(tokenEntity);
    }

    private Claims extractRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKeyRefresh())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_REFRESH_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean checkTokenInDB(String token, String email) {
        TokenEntity tokenEntity = tokenRepository.getTokenEntitiesByEmail(email);
        if (tokenEntity.getRefreshToken().equals(token)) return true;
        return false;
    }

    @Override
    public String getRoleByEmail(String email) {
        User user = userRepository.findByEmail1(email);
        switch (user.getRoleId()) {
            case 1:
                return "citizen";
            case 2:
                return "admin";
        }
        return null;
    }
    @Override
    public Date getExpiredTimeToken(String email){
        TokenEntity tokenEntity = tokenRepository.getTokenEntitiesByEmail(email);
//        if(tokenEntity == null){
//            return new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24);
//        }
        return tokenEntity.getExpriredToken();
    }
    @Override
    public void deleteToken(String email){
        TokenEntity tokenEntity = tokenRepository.getTokenEntitiesByEmail(email);
        tokenRepository.delete(tokenEntity);
    }
}
