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
	 * @param userToken the user token
	 * @return the addresses of the user
	 */
	List<Address> load(Long id, String userToken);
	
	/**
	 * Save.
	 *
	 * @param userToken the user token
	 * @param address the address
	 * @return the addresses of the user
	 */
	Address save(String userToken, Address address);

	/**
	 * Update.
	 *
	 * @param userToken the user token
	 * @param address the address
	 * @return the addresses of the user
	 */
    Address update(String userToken, Address address);

    /**
	 * Delete.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @param addressId the address id
	 */
    void delete(Long userId, String userToken, Long addressId);
    
    /**
	 * Load.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @param addressId the address id
	 * @param type the type
	 */
    void setDefault(Long userId, String userToken, Long addressId, AddressType type);
}
