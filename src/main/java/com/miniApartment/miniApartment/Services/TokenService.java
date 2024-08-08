package com.miniApartment.miniApartment.Services;

import java.util.Date;

public interface TokenService {
    void updateToken(String token, String email);

    boolean checkTokenInDB(String token, String email);

    String getRoleByEmail(String email);

    Date getExpiredTimeToken(String email);

    void deleteToken(String email);
}
