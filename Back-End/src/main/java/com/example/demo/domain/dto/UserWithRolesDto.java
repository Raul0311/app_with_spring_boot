package com.example.demo.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserWithRolesDto {
    private Long id;
    private String username;
    private List<RoleDto> roles;
}

