package com.engineer.Trinity_BE.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}
