package com.engineer.Trinity_BE.domain.auth.repository;

import com.engineer.Trinity_BE.domain.auth.entity.UserAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthTokenRepository extends JpaRepository<UserAuthToken, Long> {
    Optional<UserAuthToken> findByToken(String token);
    List<UserAuthToken> findAllByUserIdAndIsRevokedFalse(Long userId);
    Optional<UserAuthToken> findByUserIdAndIsRevokedFalse(Long userId);
}
