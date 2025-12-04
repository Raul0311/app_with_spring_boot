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
	public User load(Long id, String userToken) {
		
		return userPortOut.load(id, userToken);
	}

	
}
