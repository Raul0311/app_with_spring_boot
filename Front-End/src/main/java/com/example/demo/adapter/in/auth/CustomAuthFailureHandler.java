package com.example.demo.adapter.in.auth;

import java.io.IOException;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.AccountCreatedException;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.UserExistsException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
		
		if (exception instanceof AccountCreatedException) {
	        response.sendRedirect("/auth?registered=true");
	        return;
	    }

	    if (exception instanceof UserExistsException) {
	        response.sendRedirect("/auth/register?error=userexists");
	        return;
	    }

	    response.sendRedirect("/auth?error=true");
	}

}
