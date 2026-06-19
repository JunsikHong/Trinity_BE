package com.engineer.Trinity_BE.domain.user.controller;

import com.engineer.Trinity_BE.domain.user.dto.response.UserMeResponse;
import com.engineer.Trinity_BE.domain.user.service.UserService;
import com.engineer.Trinity_BE.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserMeResponse>> me(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success("ME", userService.me(authentication)));
    }
}
