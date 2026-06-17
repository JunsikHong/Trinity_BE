package com.engineer.Trinity_BE.domain.auth.controller;

import com.engineer.Trinity_BE.domain.auth.dto.request.LoginRequest;
import com.engineer.Trinity_BE.domain.auth.dto.request.RefreshTokenRequest;
import com.engineer.Trinity_BE.domain.auth.dto.response.TokenResponse;
import com.engineer.Trinity_BE.domain.auth.service.AuthService;
import com.engineer.Trinity_BE.domain.auth.dto.request.SignupRequest;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TokenResponse>> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(ApiResponse.success("SIGNUP", authService.signup(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("LOGIN", authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(ApiResponse.success("REFRESH_TOKEN", authService.refreshTokens(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long userId) {
        authService.logout(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success("LOGOUT", null));
    }
}
