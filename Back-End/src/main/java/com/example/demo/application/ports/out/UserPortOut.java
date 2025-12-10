package com.example.demo.application.ports.out;

import com.example.demo.domain.User;
/**
 * The Interface UserPortOut.
 */
public interface UserPortOut {
	
	/**
	 * Load.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return the user
	 */
	User load(Long userId, String userToken);
	
	/**
	 * ValidateUser.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 */
	void validateUser(Long userId, String userToken);
	
	/**
	 * Update.
	 *
	 * @param user the user
	 * @param userToken the user token
	 * @return the updated user
	 */
	void update(User user, String userToken);
	
	/**
	 * DisableUser.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return the eliminated user
	 */
	void disableUser(Long userId, String userToken);
}
