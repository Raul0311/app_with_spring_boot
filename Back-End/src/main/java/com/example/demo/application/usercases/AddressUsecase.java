package com.example.demo.application.usercases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;
import com.example.demo.application.ports.in.AddressPortIn;
import com.example.demo.application.ports.out.AddressPortOut;
import com.example.demo.domain.Address;

@Service
public class AddressUsecase implements AddressPortIn {
	
	private final AddressPortOut addressPortOut;
	
	public AddressUsecase(AddressPortOut addressPortOut) {
		this.addressPortOut = addressPortOut;
	}

	@Override
	public List<Address> load(Long userId) {
		
		return addressPortOut.load(userId);
	}

	@Override
    public void save(Address address) {
		if (address.getUserId() == null) {
            throw new IllegalArgumentException("userId es obligatorio en la dirección");
        }
		
        addressPortOut.save(address);
    }

    @Override
    public void update(Address address) {
    	if (address.getUserId() == null || address.getId() == null) {
            throw new IllegalArgumentException("userId y id de dirección son obligatorios");
        }
    	
        addressPortOut.update(address);
    }

    @Override
    public void delete(Long userId, Long addressId) {
        addressPortOut.delete(userId, addressId);
    }
    
    @Override
    public void setDefault(Long userId, Long addressId, AddressType type) {
        addressPortOut.setDefault(userId, addressId, type);
    }
}
