package com.engineer.Trinity_BE.domain.user.service;

import com.engineer.Trinity_BE.domain.user.dto.response.UserMeResponse;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserMeResponse me(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new IllegalIdentifierException("사용자를 찾을 수 없습니다."));
        return new UserMeResponse(user);
    }
}
