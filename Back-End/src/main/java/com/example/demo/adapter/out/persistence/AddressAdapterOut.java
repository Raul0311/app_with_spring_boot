package com.example.demo.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;
import com.example.demo.application.ports.out.AddressPortOut;
import com.example.demo.domain.Address;

import jakarta.transaction.Transactional;

/**
 * The Class UserAdapterOut.
 */
@Service
public class AddressAdapterOut implements AddressPortOut {
	
	@SuppressWarnings("serial")
    public class AddressSaveException extends RuntimeException {
        public AddressSaveException(String message) {
            super(message);
        }
    }

    @SuppressWarnings("serial")
    public class AddressUpdateException extends RuntimeException {
        public AddressUpdateException(String message) {
            super(message);
        }
    }

    @SuppressWarnings("serial")
    public class AddressDeleteException extends RuntimeException {
        public AddressDeleteException(String message) {
            super(message);
        }
    }
    
    @SuppressWarnings("serial")
    public class AddressDefaultChangeException extends RuntimeException {
        public AddressDefaultChangeException(String message) {
            super(message);
        }
    }

	private final AddressRepository addressRepository;

    public AddressAdapterOut(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> load(Long userId) {
        List<AddressEntity> addressesEntity = addressRepository.findByUserId(userId);

        List<Address> addresses = new ArrayList<>();

        for (AddressEntity entity : addressesEntity) {
            addresses.add(AddressMapper.toDomain(entity));
        }
        
        return addresses;
    }

    @Override
    @Transactional
    public void save(Address address) {
        
        if (address.getUserId() == null) {
            throw new IllegalArgumentException("userId es obligatorio en la dirección");
        }
        
        if (Boolean.TRUE.equals(address.getPredeterminated())) {
            // Desmarcar la anterior predeterminada del mismo tipo
            addressRepository.clearDefault(address.getUserId(), address.getType().name());
        }
        Address newAddress = AddressMapper.toDomain(addressRepository.save(AddressMapper.toEntity(address)));
        if(newAddress == null) throw new AddressSaveException("No se pudo guardar la nueva dirección.");
    }

    @Override
    @Transactional
    public void update(Address address) {
        if (!addressRepository.existsById(address.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
        
        if (Boolean.TRUE.equals(address.getPredeterminated())) {
            // Desmarcar la anterior predeterminada del mismo tipo
            addressRepository.clearDefault(address.getUserId(), address.getType().name());
        }
        
        Address updatedAddress = AddressMapper.toDomain(addressRepository.save(AddressMapper.toEntity(address)));
        if(updatedAddress == null) throw new AddressUpdateException("No se pudo actualizar la dirección.");
    }

    @Override
    @Transactional
    public void delete(Long userId, Long addressId) {
        AddressEntity addr = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (Boolean.TRUE.equals(addr.getPredeterminated())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete default address");
        }
        
        try {
        	addressRepository.deleteById(addressId);
        } catch(AddressDeleteException e) {
    		throw new AddressDeleteException("Fallo al intentar eliminar la dirección con ID: " + addressId);
    	}
    }
    
    @Override
    @Transactional
    public void setDefault(Long userId, Long addressId, AddressType type) {
    	Integer result = addressRepository.setDefaultAddress(addressId, userId, type.name());
        if(result == 0) throw new AddressDefaultChangeException("No se pudo establecer la dirección " + addressId + " como predeterminada.");
    }
}