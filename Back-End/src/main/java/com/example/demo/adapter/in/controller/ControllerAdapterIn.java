package com.example.demo.adapter.in.controller;

import java.util.List;

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
    public List<Address> getAddresses(@RequestParam Long userId, @RequestParam String userToken) {
		return addressPortIn.load(userId, userToken);
    }
	
	@PostMapping("/setAddress")
	@Operation(summary = "Crear una dirección", description = "Crea una dirección ya sea de facturación o de envío del usuario")
    public Address createAddress(@RequestParam String userToken,
                                       @RequestBody Address address) {
		System.out.println(address.getName());
		System.out.println(address.getUserId());
        return addressPortIn.save(userToken, address);
    }

    @PutMapping("/updateAddress")
    @Operation(summary = "Modificar una dirección", description = "Modifica una dirección ya sea de facturación o de envío del usuario")
    public Address updateAddress(@RequestParam String userToken,
                                       @RequestBody Address address) {
        return addressPortIn.update(userToken, address);
    }

    @DeleteMapping("/deleteAddress/{id}")
    @Operation(summary = "Eliminar una dirección", description = "Elimina una dirección ya sea de facturación o de envío del usuario")
    public void deleteAddress(@RequestParam Long userId,
                              @RequestParam String userToken,
                              @PathVariable Long id) {
        addressPortIn.delete(userId, userToken, id);
    }
    
    @PutMapping("/{id}/default")
    @Operation(summary = "Cambiar dirección predeterminada", description = "Cambia la dirección predeterminada ya sea de facturación o de envío del usuario")
    public void setDefaultAddress(@RequestParam Long userId,
                                  @RequestParam String userToken,
                                  @PathVariable Long id,
                                  @RequestParam AddressType type) {
        addressPortIn.setDefault(userId, userToken, id, type);
    }

    @GetMapping("/getUser")
    @Operation(summary = "Obtiene el usuario", description = "Obtiene el usuario por id")
    public User getUser(@RequestParam Long userId, @RequestParam String userToken) {
        // Validar que el token es correcto para ese usuario
        User user = userPortIn.load(userId, userToken);
        if (user == null) {
            // Si no se encuentra o token inválido, lanzamos excepción
            throw new RuntimeException("Usuario no encontrado o token inválido");
        }
        return user;
    }
}
