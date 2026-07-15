package com.engineer.Trinity_BE.domain.user.initial;

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
public class UserInitializer {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Department department;
        if(!departmentRepository.existsByName("관리자")) {
            department = departmentRepository.save(
                    Department.builder()
                            .name("관리자")
                            .build()
            );
        } else {
            department = departmentRepository.findByName("관리자")
                    .orElseThrow();
        }

        if(!userRepository.existsByEmail("admin@admin.com")) {
            userRepository.save(
                    User.builder()
                            .department(department)
                            .status(UserStatus.ACTIVE)
                            .role(UserRole.ADMIN)
                            .email("admin@admin.com")
                            .password(passwordEncoder.encode("1234"))
                            .name("관리자")
                            .build()
            );
        }

        if(!departmentRepository.existsByName("정비팀")) {
            department = departmentRepository.save(
                    Department.builder()
                            .name("정비팀")
                            .build()
            );
        } else {
            department = departmentRepository.findByName("정비팀")
                    .orElseThrow();
        }

        if(!userRepository.existsByEmail("test1@test1.com")) {
            userRepository.save(
                    User.builder()
                            .department(department)
                            .status(UserStatus.ACTIVE)
                            .role(UserRole.EDITOR)
                            .email("test1@test1.com")
                            .password(passwordEncoder.encode("1234"))
                            .name("테스트1")
                            .build()
            );
        }

        if(!departmentRepository.existsByName("개발팀")) {
            department = departmentRepository.save(
                    Department.builder()
                            .name("개발팀")
                            .build()
            );
        } else {
            department = departmentRepository.findByName("개발팀")
                    .orElseThrow();
        }

        if(!userRepository.existsByEmail("test2@test2.com")) {
            userRepository.save(
                    User.builder()
                            .department(department)
                            .status(UserStatus.ACTIVE)
                            .role(UserRole.VIEWER)
                            .email("test2@test2.com")
                            .password(passwordEncoder.encode("1234"))
                            .name("테스트2")
                            .build()
            );
        }
    }
}
