package com.example.demo.application.ports.in;

import java.util.List;

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;
import com.example.demo.domain.Address;

/**
 * The Interface AddressPortIn.
 */
public interface AddressPortIn {
	
	/**
	 * Load.
	 *
	 * @param userId the user id
	 * @return the addresses of the user
	 */
	List<Address> load(Long id);
	
	/**
	 * Save.
	 *
	 * @param address the address
	 */
	void save(Address address);

	/**
	 * Update.
	 *
	 * @param address the address
	 */
    void update(Address address);

    /**
	 * Delete.
	 *
	 * @param userId the user id
	 * @param addressId the address id
	 */
    void delete(Long userId, Long addressId);
    
    /**
	 * Load.
	 *
	 * @param userId the user id
	 * @param addressId the address id
	 * @param type the type
	 */
    void setDefault(Long userId, Long addressId, AddressType type);
}
