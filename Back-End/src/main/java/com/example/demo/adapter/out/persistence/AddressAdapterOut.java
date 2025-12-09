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

	private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressAdapterOut(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void validateUser(Long userId, String userToken) {
        Integer result = userRepository.validateUserTokenByUserIdAndUserToken(userId, userToken);
        boolean valid = result != null && result == 1;
        if (!valid) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user token");
    }

    @Override
    public List<Address> load(Long userId, String userToken) {
        validateUser(userId, userToken);
        List<AddressEntity> addressesEntity = addressRepository.findByUserId(userId);

        List<Address> addresses = new ArrayList<>();

        for (AddressEntity entity : addressesEntity) {
            addresses.add(AddressMapper.toDomain(entity));
        }
        
        return addresses;
    }

    @Override
    @Transactional
    public Address save(String userToken, Address address) {
        validateUser(address.getUserId(), userToken);
        
        if (address.getUserId() == null) {
            throw new IllegalArgumentException("userId es obligatorio en la dirección");
        }
        
        if (Boolean.TRUE.equals(address.getPredeterminated())) {
            // Desmarcar la anterior predeterminada del mismo tipo
            addressRepository.clearDefault(address.getUserId(), address.getType().name());
        }
        
        return AddressMapper.toDomain(addressRepository.save(AddressMapper.toEntity(address)));
    }

    @Override
    @Transactional
    public Address update(String userToken, Address address) {
        validateUser(address.getUserId(), userToken);
        if (!addressRepository.existsById(address.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
        
        if (Boolean.TRUE.equals(address.getPredeterminated())) {
            // Desmarcar la anterior predeterminada del mismo tipo
            addressRepository.clearDefault(address.getUserId(), address.getType().name());
        }

        return AddressMapper.toDomain(addressRepository.save(AddressMapper.toEntity(address)));
    }

    @Override
    @Transactional
    public void delete(Long userId, String userToken, Long addressId) {
        validateUser(userId, userToken);
        AddressEntity addr = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (Boolean.TRUE.equals(addr.getPredeterminated())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete default address");
        }

        addressRepository.deleteById(addressId);
    }
    
    @Override
    @Transactional
    public void setDefault(Long userId, String userToken, Long addressId, AddressType type) {
        validateUser(userId, userToken);
        addressRepository.setDefaultAddress(addressId, userId, type.name());
    }
}