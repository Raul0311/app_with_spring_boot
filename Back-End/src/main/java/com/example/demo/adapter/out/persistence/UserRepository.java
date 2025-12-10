package com.example.demo.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	/**
	 * Validate the userToken.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return if the userToken is validate
	 */
	@Query(value="SELECT CASE WHEN EXISTS(SELECT 1 FROM user_tokens WHERE user_id = ?1 AND user_token = ?2) THEN TRUE ELSE FALSE END", nativeQuery = true)
	Integer validateUserTokenByUserIdAndUserToken(Long userId, String userToken);
	
	/**
	 * Disable user.
	 *
	 * @param userId the user id
	 * @return if the user is disabled
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE users u SET u.enabled = false WHERE u.id = ?1", nativeQuery = true)
	Integer disableUser(Long userId);
	
	/**
	 * Update user profile fields.
	 *
	 * @param username the username
	 * @param name the name
	 * @param lastname1 the first lastname
	 * @param lastname2 the second lastname
	 * @param email the email
	 * @param phone the phone
	 * @param address the address
	 * @param numberAddress the address number
	 * @param apartment the apartment
	 * @param city the city
	 * @param zipCode the zip code
	 * @param country the country
	 * @param userId the user id
	 * @return the user updated
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE users u SET u.username = ?1, u.name = ?2, u.lastname1 = ?3, u.lastname2 = ?4, "
			+ "u.email = ?5, u.phone = ?6, u.address = ?7, u.number_address = ?8, u.apartment = ?9, "
			+ "u.city = ?10, u.zip_code = ?11, u.country = ?12 WHERE u.id = ?13", nativeQuery = true)
	Integer updateUser(String username, String name, String lastname1, String lastname2, String email, String phone, String address, 
			String numberAddress, String apartment, String city, String zipCode, String country, Long userId);
}