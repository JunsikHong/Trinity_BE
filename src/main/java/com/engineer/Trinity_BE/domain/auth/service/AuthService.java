package com.engineer.Trinity_BE.domain.auth.service;

import com.engineer.Trinity_BE.domain.auth.dto.request.LoginRequest;
import com.engineer.Trinity_BE.domain.auth.dto.request.RefreshTokenRequest;
import com.engineer.Trinity_BE.domain.auth.dto.request.SignupRequest;
import com.engineer.Trinity_BE.domain.auth.dto.response.TokenResponse;
import com.engineer.Trinity_BE.domain.auth.entity.UserAuthToken;
import com.engineer.Trinity_BE.domain.auth.exception.AuthException;
import com.engineer.Trinity_BE.domain.auth.repository.UserAuthTokenRepository;
import com.engineer.Trinity_BE.domain.user.entity.Department;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.enums.UserRole;
import com.engineer.Trinity_BE.domain.user.enums.UserStatus;
import com.engineer.Trinity_BE.domain.user.repository.DepartmentRepository;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import com.engineer.Trinity_BE.global.security.token.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final UserAuthTokenRepository userAuthTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 부서입니다."));

        User user = User.builder()
                .department(department)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .status(UserStatus.ACTIVE)
                .role(UserRole.VIEWER)
                .build();

        userRepository.save(user);
        return issueTokens(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        validateUserStatus(user);
        return issueTokens(user);
    }

    @Transactional
    public TokenResponse refreshTokens(RefreshTokenRequest request) {
        if(!jwtProvider.validateToken(request.getRefreshToken())) {
            throw new AuthException("유효하지 않은 Refresh Token 입니다.");
        }

        UserAuthToken userAuthToken = userAuthTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthException("존재하지 않는 Refresh Token 입니다."));

        if(!userAuthToken.isValid()) {
            if(userAuthToken.getIsRevoked()) {
                userAuthTokenRepository.findAllByUser_IdAndIsRevokedFalse(userAuthToken.getUser().getId())
                        .forEach(t -> {
                            t.revoke();
                            userAuthTokenRepository.save(t);
                        });
            }
            throw new AuthException("만료되었거나 폐기된 Refresh Token 입니다.");
        }

        userAuthToken.revoke();
        userAuthTokenRepository.save(userAuthToken);

        User user = userAuthToken.getUser();
        return issueTokens(user);
    }

    @Transactional
    public void logout(Long userId) {
        userAuthTokenRepository.findByUser_IdAndIsRevokedFalse(userId)
                .ifPresent(t -> {
                    t.revoke();
                    userAuthTokenRepository.save(t);
                });
    }

    private TokenResponse issueTokens(User user) {
        userAuthTokenRepository.findByUser_IdAndIsRevokedFalse(user.getId())
                .ifPresent(t -> {
                    t.revoke();
                    userAuthTokenRepository.save(t);
                });

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getStatus().name(), user.getRole().name());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenExpiry() / 1000);
        userAuthTokenRepository.save(UserAuthToken.create(user, refreshToken, expiresAt));

        return new TokenResponse(accessToken, refreshToken, user.getStatus().name(), user.getRole().name());
    }

    private void validateUserStatus(User user) {
        if(user.isInactive()) {
            throw new AuthException("비활성화된 계정입니다.");
        }

        if(user.isBanned()) {
            throw new AuthException("정지된 계정입니다.");
        }
    }

}
