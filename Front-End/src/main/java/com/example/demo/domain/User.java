package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user in the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

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

    private List<String> roles = new ArrayList<>();
}

