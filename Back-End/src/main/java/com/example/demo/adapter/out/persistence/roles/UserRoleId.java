package com.example.demo.adapter.out.persistence.roles;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Debe implementar Serializable
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

    private Long userId; // Debe coincidir con el nombre del campo en UserRolesEntity
    private Long roleId; // Debe coincidir con el nombre del campo en UserRolesEntity

    // Implementación de equals y hashCode es OBLIGATORIA para claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}