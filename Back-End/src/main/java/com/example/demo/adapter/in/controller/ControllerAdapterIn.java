package com.example.demo.adapter.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.adapter.out.persistence.addresses.AddressEntity.AddressType;
import com.example.demo.application.ports.in.AddressPortIn;
import com.example.demo.application.ports.in.RolesPortIn;
import com.example.demo.application.ports.in.UserPortIn;
import com.example.demo.application.rolecases.RoleUpdateCommand;
import com.example.demo.domain.Address;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.RoleCreationDto;
import com.example.demo.domain.dto.RoleDto;
import com.example.demo.domain.dto.UserWithRolesDto;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api")
public class ControllerAdapterIn {
	private final AddressPortIn addressPortIn;
	private final UserPortIn userPortIn;
	private final RolesPortIn rolesPortIn;
	
	public ControllerAdapterIn(AddressPortIn addressPortIn, UserPortIn userPortIn, RolesPortIn rolesPortIn) {
        this.addressPortIn = addressPortIn;
        this.userPortIn = userPortIn;
        this.rolesPortIn = rolesPortIn;
    }

	@GetMapping("/getAddresses")
    @Operation(summary = "Obtener direcciones", description = "Devuelve las direcciones de facturación y de envío del usuario")
    public List<Address> getAddresses(Authentication authentication) {
		Long userId = (Long) authentication.getPrincipal();
		
		return addressPortIn.load(userId);
    }
	
	@PostMapping("/setAddress")
	@Operation(summary = "Crear una dirección", description = "Crea una dirección ya sea de facturación o de envío del usuario")
    public Address createAddress(Authentication authentication,
                                       @RequestBody Address address) {
		Long userId = (Long) authentication.getPrincipal();
		address.setUserId(userId);
		
        return addressPortIn.save(address);
    }

    @PutMapping("/updateAddress")
    @Operation(summary = "Modificar una dirección", description = "Modifica una dirección ya sea de facturación o de envío del usuario")
    public Address updateAddress(Authentication authentication,
                                       @RequestBody Address address) {
    	
        return addressPortIn.update(address);
    }

    @DeleteMapping("/deleteAddress/{id}")
    @Operation(summary = "Eliminar una dirección", description = "Elimina una dirección ya sea de facturación o de envío del usuario")
    public ResponseEntity<Void> deleteAddress(Authentication authentication, @PathVariable Long id) {
    	Long userId = (Long) authentication.getPrincipal();
    	
        addressPortIn.delete(userId, id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/default")
    @Operation(summary = "Cambiar dirección predeterminada", description = "Cambia la dirección predeterminada ya sea de facturación o de envío del usuario")
    public ResponseEntity<Void> setDefaultAddress(Authentication authentication,
                                  @PathVariable Long id,
                                  @RequestParam AddressType type) {
    	Long userId = (Long) authentication.getPrincipal();
    	
        addressPortIn.setDefault(userId, id, type);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUser")
    @Operation(summary = "Obtiene el usuario", description = "Obtiene el usuario por id")
    public User getUser(Authentication authentication) {
    	Long userId = (Long) authentication.getPrincipal();
    	
        return userPortIn.load(userId);
    }
    
    @PutMapping("/updateUser")
    @Operation(summary = "Modificar el usuario", description = "Modifica el usuario por id")
    public User updateUser(Authentication authentication, @RequestBody User user) {
    	
        return userPortIn.update(user);
    }
    
    @PutMapping("/users/disable")
    @Operation(summary = "Eliminar cuenta del usuario", description = "Elimina la cuenta del usuario poniendo el campo enabled a false")
    public ResponseEntity<Void> disableUser(Authentication authentication) {
    	Long userId = (Long) authentication.getPrincipal();
    	
        userPortIn.disableUser(userId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/admin/usersWithRoles")
    @Operation(summary = "Obtener usuarios con roles", description = "Devuelve todos los usuarios con los roles de cada uno")
    public ResponseEntity<List<UserWithRolesDto>> listAllUsersWithRoles(Authentication authentication) {

        return ResponseEntity.ok(rolesPortIn.getAllUsersWithRoles());
    }
    
    @GetMapping("/admin/roles")
    @Operation(summary = "Obtener roles", description = "Devuelve todos los roles que tiene la aplicación")
    public ResponseEntity<List<RoleDto>> listAllRoles(Authentication authentication) {

        return ResponseEntity.ok(rolesPortIn.getAllRoles());
    }
    
    @PutMapping("/admin/updateUserRoles")
    @Operation(summary = "Actualizar roles", description = "Actualiza los roles que se han cambiado")
    public ResponseEntity<Void> updateUserRoles(Authentication authentication, @RequestBody RoleUpdateCommand command) {
    	Long authenticatedUserId = (Long) authentication.getPrincipal();
    	
    	rolesPortIn.updateUserRoles(authenticatedUserId, command);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/admin/roles") 
    @Operation(summary = "Crear un nuevo rol", description = "Crea un nuevo rol con nombre y descripción.")
    public ResponseEntity<Void> createNewRole(Authentication authentication, @RequestBody RoleCreationDto roleDto) {
        rolesPortIn.createNewRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @DeleteMapping("/admin/roles/{roleName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") 
    @Operation(summary = "Eliminar un rol", description = "Elimina un rol existente que no sea ROLE_ADMIN o ROLE_USER.")
    public ResponseEntity<Void> deleteRole(
        Authentication authentication, 
        @PathVariable String roleName
    ) {
        rolesPortIn.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }
}
