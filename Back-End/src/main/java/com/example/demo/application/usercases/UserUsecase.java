package com.example.demo.application.usercases;

import org.springframework.stereotype.Service;

import com.example.demo.application.ports.in.UserPortIn;
import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

/**
 * The Class UserUsecase.
 */
@Service
public class UserUsecase implements UserPortIn {
	
	/** The user port out. */
	private final UserPortOut userPortOut;
	
	/**
	 * Instantiates a new user usecase.
	 *
	 * @param userPortOut the user port out
	 */
	public UserUsecase(UserPortOut userPortOut) {
		this.userPortOut = userPortOut;
	}

	/**
	 * Load.
	 *
	 * @param id the id
	 * @return the user
	 */
	@Override
	public User load(Long id) {
		
		return userPortOut.load(id);
	}

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	public User update(User user) {
		
		return userPortOut.update(user);
	}

	/**
	 * Disable user.
	 *
	 * @param userId the user id
	 */
	@Override
	public void disableUser(Long userId) {
		
		userPortOut.disableUser(userId);
	}
}
