package com.example.demo.adapter.out.persistence;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO that represents a user returned from stored procedures.
 * It has the same structure as UserEntity but without JPA annotations.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String username;
    private String passw;
    private String name;
    private String lastname1;
    private String lastname2;
    private String city;
    private String country;
    private String address;
    private String numberAddress;
    private String apartment;
    private String zipCode;
    private String phone;
    private String email;
    private Boolean enabled;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userToken;
    private String rolesStr;
}
