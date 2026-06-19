package com.engineer.Trinity_BE.domain.auth.initial;

import com.engineer.Trinity_BE.domain.user.entity.Department;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.enums.UserRole;
import com.engineer.Trinity_BE.domain.user.enums.UserStatus;
import com.engineer.Trinity_BE.domain.user.repository.DepartmentRepository;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @PostConstruct
    public void init() {
        if(userRepository.existsByEmail("admin@admin.com")) {
            return;
        }

        if(departmentRepository.existsByName("관리자")) {
            return;
        }

        Department adminDepartment = Department.builder()
                .name("관리자")
                .build();

        departmentRepository.save(adminDepartment);

        User admin = User.builder()
                .department(adminDepartment)
                .email("admin@admin.com")
                .password(passwordEncoder.encode("1234"))
                .role(UserRole.ADMIN)
                .status(UserStatus.ACTIVE)
                .name("관리자")
                .build();

        userRepository.save(admin);
    }
}
