package com.example.demo.application.ports.out;

import java.util.List;

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;
import com.example.demo.domain.Address;
/**
 * The Interface UserPortOut.
 */
public interface AddressPortOut {
	
	/**
	 * Load.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return the addresses
	 */
	List<Address> load(Long userId, String userToken);
	
	/**
	 * ValidateUser.
	 *
	 * @param userId the userId
	 * @param userToken the user token
	 */
	void validateUser(Long userId, String userToken);
	
	/**
	 * Save.
	 *
	 * @param userId the user id
	 * @return the addresses
	 */
	Address save(String userToken, Address address);

	/**
	 * Update.
	 *
	 * @param userId the user id
	 * @return the addresses
	 */
	Address update(String userToken, Address address);

    /**
	 * Delete.
	 *
	 * @param userId the user id
	 */
    void delete(Long userId, String userToken, Long addressId);
    
    /**
	 * SetDefault.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @param addressId the address id
	 * @param type the type
	 */
    void setDefault(Long userId, String userToken, Long addressId, AddressType type);
}
