package com.engineer.Trinity_BE.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}
