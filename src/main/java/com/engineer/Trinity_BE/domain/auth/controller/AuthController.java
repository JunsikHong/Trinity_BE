package com.engineer.Trinity_BE.domain.auth.controller;

import com.engineer.Trinity_BE.domain.auth.service.AuthService;
import com.engineer.Trinity_BE.domain.auth.dto.request.SignupRequest;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> signup(
            @RequestBody
            SignupRequest request
    ) {
        authService.signup(request);
        return ApiResponse.success("회원가입 성공", null);
    }
}
