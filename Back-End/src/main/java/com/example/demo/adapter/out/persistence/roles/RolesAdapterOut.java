package com.example.demo.adapter.out.persistence.roles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.adapter.out.persistence.user.UserEntity;
import com.example.demo.adapter.out.persistence.user.UserRepository;
import com.example.demo.application.ports.out.RolesPortOut;
import com.example.demo.domain.dto.RoleDto;
import com.example.demo.domain.dto.UserWithRolesDto;

@Service
public class RolesAdapterOut implements RolesPortOut {
	
    // Asegúrate de que los constructores inyecten estos tres
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;

    public RolesAdapterOut(UserRepository userRepository, RoleRepository roleRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRolesRepository = userRolesRepository;
    }
    
    private UserWithRolesDto mapUserEntityToDto(UserEntity user) {
        UserWithRolesDto dto = new UserWithRolesDto(user.getId(), user.getUsername(), null); 
        
        List<RoleDto> roleDtos = user.getRoles().stream()
            .map(r -> new RoleDto(r.getRoleName(), r.getDescription()))
            .collect(Collectors.toList());
        
        dto.setRoles(roleDtos);
        return dto;
    }

    @Override
    public List<UserWithRolesDto> loadAllUsersWithRoles() {
    	List<UserEntity> users = userRepository.findAllWithRoles(); 
        return users.stream().map(this::mapUserEntityToDto).collect(Collectors.toList());
    }
    
    @Override
    public UserWithRolesDto loadUserWithRoles(Long userId) {
        UserEntity user = userRepository.findByIdWithRoles(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return mapUserEntityToDto(user);
    }
    
    @Override
    public List<RoleDto> loadAllRoles() {
        // Consulta simple a la tabla 'roles'
        return roleRepository.findAll().stream()
            .map(r -> new RoleDto(r.getRoleName(), r.getDescription()))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeSpecificRolesFromUser(Long userId, List<String> roleNames) {
    	// Obtenemos los IDs de los roles a partir de sus nombres
    	List<Long> roleIds = roleRepository.findIdsByRoleNames(roleNames);
        
        // Usamos el nuevo método en el repositorio
    	userRolesRepository.deleteByUserIdAndRoleIds(userId, roleIds);
    }

    @Override
    @Transactional
    public void assignRolesToUser(Long userId, List<String> roleNames) {
    	// 1. Cargar la entidad UserEntity (Una sola vez)
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); // Usa la excepción que corresponda

        for (String roleName : roleNames) {
            Long roleId = findRoleIdByName(roleName);
            if (roleId != null) {
                // 2. Cargar la entidad RoleEntity
                RoleEntity role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found")); // Usa la excepción que corresponda

                // 3. Crear UserRolesEntity y ASIGNAR LAS ENTIDADES ASOCIADAS
                UserRolesEntity newRole = new UserRolesEntity(user, role); 
                
                userRolesRepository.save(newRole);
            }
        }
    }

    @Override
    public Long findRoleIdByName(String roleName) {
        // Usa el nuevo método en RoleRepository
        return roleRepository.findByName(roleName).map(RoleEntity::getId).orElse(null);
    }
    
    @Override
    @Transactional
    public void saveRole(String roleName, String description) {
        RoleEntity newRole = new RoleEntity();
        newRole.setRoleName(roleName);
        newRole.setDescription(description);
        
        roleRepository.save(newRole);
    }
    
    @Override
    @Transactional
    public void removeRole(String roleName) {
        // Esto asume que RoleRepository tiene un método para eliminar por nombre
        // Opcional: Podrías necesitar primero cargar la entidad y luego borrarla
        roleRepository.deleteByRoleName(roleName); 
    }
}