package com.example.demo.application.usercases;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.application.ports.in.UserPortIn;

@Service
public class UserUsecase implements UserPortIn {

	@Override
	public String load(Authentication auth)throws BadCredentialsException {
		try {
	        // Solo redirige a /private/home si está autenticado
	        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
	        	return "redirect:/private/home";
	        }
	    	
	    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            String path = attr.getRequest().getRequestURI();
            
            if("/auth/register".equals(path)) {
                return "register";
            }
            
            if("/auth/forgot-password".equals(path)) {
                return "forgot-password";
            }
            
            if("/auth/reset-password".equals(path)) {
                return "reset-password";
            }
	        
	        return "auth";
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
        
		return null;
	}
}
