package com.example.demo.adapter.in.auth;

import java.io.IOException;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.AccountCreatedException;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.EmailDoesNotExist;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.LinkSentSuccessfully;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.PasswordDoesNotReset;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider.ResetPasswordSuccessfully;
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
	    
	    if (exception instanceof LinkSentSuccessfully) {
	        response.sendRedirect("/auth/forgot-password?msg=sent");
	        return;
	    }

	    if (exception instanceof EmailDoesNotExist) {
	        response.sendRedirect("/auth/forgot-password?error=notfound");
	        return;
	    }
	    
	    if (exception instanceof ResetPasswordSuccessfully) {
	        response.sendRedirect("/auth/reset-password?msg=resetpass");
	        return;
	    }

	    if (exception instanceof PasswordDoesNotReset) {
	        response.sendRedirect("/auth/reset-password?error=true");
	        return;
	    }

	    response.sendRedirect("/auth?error=true");
	}

}
