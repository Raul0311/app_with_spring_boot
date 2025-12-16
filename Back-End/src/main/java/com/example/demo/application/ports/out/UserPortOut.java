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
	 * @return the user
	 */
	User load(Long userId);
	
	/**
	 * ValidateUser.
	 *
	 * @param userToken the user token
	 */
	Long validateUser(String userToken);
	
	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the updated user
	 */
	User update(User user);
	
	/**
	 * DisableUser.
	 *
	 * @param userId the user id
	 * @return the eliminated user
	 */
	void disableUser(Long userId);
}
