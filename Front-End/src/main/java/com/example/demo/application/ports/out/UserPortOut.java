package com.example.demo.application.ports.out;

import com.example.demo.domain.User;

/**
 * The Interface LoadUser.
 */
public interface UserPortOut {
	
	/**
	 * Load.
	 *
	 * @param user the user
	 * @param session_id the session id
	 * @param register the register
	 * @param login the login
	 * @return the user
	 */
	User load(User user, String session_id, boolean register, boolean login);
	
	/**
	 * deleteUserToken.
	 *
	 * @param session_id the session_id
	 */
	void deleteUserToken(String session_id);
}
