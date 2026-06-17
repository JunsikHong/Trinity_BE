package com.engineer.Trinity_BE.domain.auth.service;

import com.engineer.Trinity_BE.domain.auth.dto.request.SignupRequest;
import com.engineer.Trinity_BE.domain.user.entity.Department;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.enums.UserRole;
import com.engineer.Trinity_BE.domain.user.repository.DepartmentRepository;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        Department department = departmentRepository.findByName(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 부서입니다."));

        User user = User.builder()
                .departmentId(department.getId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }


}
