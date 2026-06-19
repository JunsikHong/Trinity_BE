package com.engineer.Trinity_BE.domain.user.dto.response;

import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class UserMeResponse {

    private final Long id;
    private final String departmentName;
    private final UserRole role;
    private final String email;
    private final String name;

    public UserMeResponse(User user) {
        this.id = user.getId();
        this.departmentName = user.getDepartment().getName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
