package com.example.demo.application.usercases;

import org.springframework.stereotype.Service;

import com.example.demo.application.ports.in.UserPortIn;
import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

@Service
public class UserUsecase implements UserPortIn {
	
	private final UserPortOut userPortOut;
	
	public UserUsecase(UserPortOut userPortOut) {
		this.userPortOut = userPortOut;
	}

	@Override
	public User load(Long id) {
		
		return userPortOut.load(id);
	}

	@Override
	public User update(User user) {
		
		return userPortOut.update(user);
	}

	@Override
	public void disableUser(Long userId) {
		
		userPortOut.disableUser(userId);
	}
}
