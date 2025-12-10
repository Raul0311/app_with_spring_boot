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
	 * @return the user
	 */
	User load(Long id);
	
	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the updated user
	 */
	void update(User user);
	
	/**
	 * DisableUser.
	 *
	 * @param userId the user id
	 * @return the eliminated user
	 */
	void disableUser(Long userId);
}
