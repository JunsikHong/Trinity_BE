package com.engineer.Trinity_BE.domain.auth.entity;

import com.engineer.Trinity_BE.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "user_auth_tokens")
public class UserAuthToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "is_revoked")
    private Boolean isRevoked;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public static UserAuthToken create(User user, String token, LocalDateTime expiresAt) {
        UserAuthToken userAuthToken = new UserAuthToken();
        userAuthToken.user = user;
        userAuthToken.token = token;
        userAuthToken.isRevoked = false;
        userAuthToken.createdAt = LocalDateTime.now();
        userAuthToken.expiresAt = expiresAt;
        return userAuthToken;
    }

    public void revoke() {
        this.isRevoked = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public boolean isValid() {
        return !isRevoked && !isExpired();
    }

}
