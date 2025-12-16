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
	 * @param sessionId the session id
	 * @param register the register
	 * @param login the login
	 * @return the user
	 */
	String load(User user, String sessionId, boolean register, boolean login);
	
	/**
	 * deleteUserToken.
	 *
	 * @param sessionId the sessionId
	 */
	void deleteUserToken(String sessionId);
	
	/**
	 * forgotPassword.
	 *
	 * @param email the email
	 * @return the string
	 */
	String forgotPassword(String email);
	
	/**
	 * resetPassword.
	 *
	 * @param newPass the newPass
	 * @param token the token
	 * @return the integer
	 */
	Integer resetPassword(String newPass, String token);
}
