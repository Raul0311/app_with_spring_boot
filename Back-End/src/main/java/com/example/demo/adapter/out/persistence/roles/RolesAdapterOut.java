package com.example.demo.adapter.out.persistence.roles;

import java.util.List;

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
    private final RoleMapper roleMapper;

    public RolesAdapterOut(UserRepository userRepository, RoleRepository roleRepository, UserRolesRepository userRolesRepository, 
    		RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRolesRepository = userRolesRepository;
        this.roleMapper = roleMapper;
    }
    
    private UserWithRolesDto mapUserEntityToDto(UserEntity user) {
        List<RoleDto> roleDtos = user.getRoles().stream()
                .map(roleMapper::toDto)
                .toList();
        
        UserWithRolesDto dto = new UserWithRolesDto(user.getId(), user.getUsername(), null); 
        dto.setRoles(roleDtos);
        return dto;
    }

    @Override
    public List<UserWithRolesDto> loadAllUsersWithRoles() {
    	return userRepository.findAllWithRoles().stream()
                .map(this::mapUserEntityToDto)
                .toList();
    }
    
    @Override
    public UserWithRolesDto loadUserWithRoles(Long userId) {
    	return userRepository.findByIdWithRoles(userId)
                .map(this::mapUserEntityToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    public List<RoleDto> loadAllRoles() {
    	return roleMapper.toDtoList(roleRepository.findAll());
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
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        roleNames.forEach(name -> 
            roleRepository.findByName(name).ifPresent(role -> {
                UserRolesEntity newRole = new UserRolesEntity(user, role);
                userRolesRepository.save(newRole);
            })
        );
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