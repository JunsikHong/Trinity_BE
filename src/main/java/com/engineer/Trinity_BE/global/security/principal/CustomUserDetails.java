package com.engineer.Trinity_BE.global.security.principal;

import com.engineer.Trinity_BE.domain.user.entity.User;
import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String userStatus;
    private final String userRole;

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.userStatus = user.getStatus().name();
        this.userRole = user.getRole().name();
    }

    public CustomUserDetails(Long userId, String userStatus, String userRole) {
        this.userId = userId;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
