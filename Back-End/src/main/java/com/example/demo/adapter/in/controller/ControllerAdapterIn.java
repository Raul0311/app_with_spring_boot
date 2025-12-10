package com.example.demo.adapter.in.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;
import com.example.demo.application.ports.in.AddressPortIn;
import com.example.demo.application.ports.in.UserPortIn;
import com.example.demo.domain.Address;
import com.example.demo.domain.User;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api")
public class ControllerAdapterIn {
	private final AddressPortIn addressPortIn;
	private final UserPortIn userPortIn;
	
	public ControllerAdapterIn(AddressPortIn addressPortIn, UserPortIn userPortIn) {
        this.addressPortIn = addressPortIn;
        this.userPortIn = userPortIn;
    }

	@GetMapping("/getAddresses")
    @Operation(summary = "Obtener direcciones", description = "Devuelve las direcciones de facturación y de envío del usuario")
    public List<Address> getAddresses(Authentication authentication) {
		Long userId = (Long) authentication.getPrincipal();
		
		return addressPortIn.load(userId);
    }
	
	@PostMapping("/setAddress")
	@Operation(summary = "Crear una dirección", description = "Crea una dirección ya sea de facturación o de envío del usuario")
    public ResponseEntity<Void> createAddress(Authentication authentication,
                                       @RequestBody Address address) {
		Long userId = (Long) authentication.getPrincipal();
		address.setUserId(userId);
		addressPortIn.save(address);
		
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateAddress")
    @Operation(summary = "Modificar una dirección", description = "Modifica una dirección ya sea de facturación o de envío del usuario")
    public ResponseEntity<Void> updateAddress(Authentication authentication,
                                       @RequestBody Address address) {
    	Long userId = (Long) authentication.getPrincipal();
    	
    	if (address.getUserId() == null || !address.getUserId().equals(userId)) {
            // Esto es un error de autorización. Debería ser 403 Forbidden.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    	
        addressPortIn.update(address);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> updateUser(Authentication authentication, @RequestBody User user) {
    	Long userId = (Long) authentication.getPrincipal();
    	
    	if (!user.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }
    	
    	userPortIn.update(user);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/users/disable")
    @Operation(summary = "Eliminar cuenta del usuario", description = "Elimina la cuenta del usuario poniendo el campo enabled a false")
    public ResponseEntity<Void> disableUser(Authentication authentication) {
    	Long userId = (Long) authentication.getPrincipal();
    	
        userPortIn.disableUser(userId);
        return ResponseEntity.ok().build();
    }
}
