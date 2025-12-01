package com.example.demo.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	/**
	 * Find by user.
	 *
	 * @param username       the username
	 * @param passw          the password
	 * @param name           the user's first name
	 * @param lastname1      the user's first last name
	 * @param lastname2      the user's second last name
	 * @param city           the user's city
	 * @param country        the user's country
	 * @param address        the user's street address
	 * @param numberAddress  the user's street number
	 * @param apartment      the user's apartment (optional)
	 * @param zipCode        the user's postal code
	 * @param phone          the user's phone number
	 * @param email          the user's email
	 * @param session_id     the current session identifier
	 * @param register       flag that indicates whether the request is a registration action
	 * @param login          flag that indicates whether the user wants to log in
	 * @return               the UserDTO containing the user data, token and roles
	 */

	// @Query(value="SELECT *, now(3) AS userToken FROM users u WHERE u.username = ?1 AND u.password = ?2", nativeQuery = true)
	@Query(value="CALL loadJsonUser(?1, ?2, ?3, ?4, ?5, ?6, ?7,"
			+ " ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16)", nativeQuery = true)
	UserDTO loadUserByUsernameAndPassword(String username, String passw, String name, String lastname1, 
			String lastname2, String city, String country, String address, String numberAddress, String apartment, 
			String zipCode, String phone, String email, String session_id, boolean register, boolean login);
	
	/**
	 * Delete userToken.
	 *
	 * @param session_id the session id
	 */
	@Query(value="DELETE FROM userTokens AS u WHERE u.session_id = ?1", nativeQuery = true)
	@Modifying
	void deleteUserToken(String session_id); 
}