package com.example.demo.application.ports.in;

import com.example.demo.domain.User;

/**
 * The Interface UserPortIn.
 */
public interface UserPortIn {
	
	/**
	 * Load.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return the user
	 */
	User load(Long id, String userToken);
	
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
