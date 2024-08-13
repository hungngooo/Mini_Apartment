package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        email = user.getEmail();
        password = user.getPassword();
        this.authorities = getAuthoritiesFromRoleId(user.getRoleId());
    }
    private List<GrantedAuthority> getAuthoritiesFromRoleId(int roleId) {
        // Ánh xạ roleId sang quyền tương ứng
        switch (roleId) {
            case 1:
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CITIZEN"));
            case 2:
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            // Thêm các role khác nếu cần
            default:
                return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CITIZEN"));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
