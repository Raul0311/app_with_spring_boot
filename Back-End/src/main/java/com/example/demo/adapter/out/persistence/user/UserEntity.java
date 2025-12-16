package com.example.demo.adapter.out.persistence.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.adapter.out.persistence.roles.RoleEntity;
import com.example.demo.adapter.out.persistence.roles.UserRolesEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @Column(name = "passw", nullable = false)
    private String passw;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "lastname1", nullable = false)
    private String lastname1;
    
    @Column(name = "lastname2")
    private String lastname2;

    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "number_address", nullable = false)
    private String numberAddress;
    
    @Column(name = "apartment")
    private String apartment;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true) // 'user' es el campo ManyToOne en UserRolesEntity
    private List<UserRolesEntity> userRoles;
    
    public List<RoleEntity> getRoles() {
        if (this.userRoles == null) return List.of();
        // Mapea la lista de UserRolesEntity a la lista de RoleEntity
        return this.userRoles.stream()
                .map(UserRolesEntity::getRole)
                .collect(Collectors.toList());
    }
}
