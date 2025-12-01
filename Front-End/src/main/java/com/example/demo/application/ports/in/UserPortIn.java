package com.example.demo.application.ports.in;

import org.springframework.security.core.Authentication;

/**
 * The Interface LoadUser.
 */
public interface UserPortIn {
	
	/**
	 * Load.
	 *
	 * @param auth the auth
	 * @return the URL to load de ModelAndView
	 */
	String load(Authentication auth);
}
