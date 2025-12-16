package com.example.demo.adapter.out.persistence.roles;

import com.example.demo.adapter.out.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class) 
public class UserRolesEntity {

    // --- Clave Compuesta ---
    
    @Id // PRIMER COMPONENTE DE LA CLAVE
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id // SEGUNDO COMPONENTE DE LA CLAVE
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    
    // --- Relaciones ---
    
    // Mapea la clave userId a la entidad User
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Mapea este ManyToOne al campo 'userId' de la clave
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    // Mapea la clave roleId a la entidad Role
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId") // Mapea este ManyToOne al campo 'roleId' de la clave
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    
    public UserRolesEntity(UserEntity user, RoleEntity role) {
        // 1. Asignar las entidades asociadas (NECESARIO para @MapsId)
        this.user = user;
        this.role = role;

        // 2. Inicializar los IDs primitivos para consistencia 
        // (aunque @MapsId los sobrescribe/usa, es buena práctica)
        this.userId = user.getId();
        this.roleId = role.getId();
    }
}