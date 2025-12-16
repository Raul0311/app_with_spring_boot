package com.example.demo.adapter.in.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.application.ports.out.EmailPortOut;
import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The Class CustomAuthenticationProvider.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@SuppressWarnings("serial")
	public static class AccountCreatedException extends AuthenticationException {
        public AccountCreatedException(String msg) {
            super(msg);
        }
    }
    
    @SuppressWarnings("serial")
    public static class UserExistsException extends AuthenticationException {
        public UserExistsException(String msg) {
            super(msg);
        }
    }
    
    @SuppressWarnings("serial")
    public static class LinkSentSuccessfully extends AuthenticationException {
        public LinkSentSuccessfully(String msg) {
            super(msg);
        }
    }
    
    @SuppressWarnings("serial")
    public static class EmailDoesNotExist extends AuthenticationException {
        public EmailDoesNotExist(String msg) {
            super(msg);
        }
    }
    
    @SuppressWarnings("serial")
    public static class ResetPasswordSuccessfully extends AuthenticationException {
        public ResetPasswordSuccessfully(String msg) {
            super(msg);
        }
    }
    
    @SuppressWarnings("serial")
    public static class PasswordDoesNotReset extends AuthenticationException {
        public PasswordDoesNotReset(String msg) {
            super(msg);
        }
    }
    
	User user;

    /** The authorities. */
    private List<GrantedAuthority> authorities;
    
    @Autowired
    private UserPortOut userPortOut;
    
    @Autowired
    private EmailPortOut emailPortOut;
    
    private boolean register;
    
    private boolean login;
    
    private boolean forgotPassword;
    
    private boolean resetPassword;

    private final String USER_EXISTS = "El usuario ya existe";
    private final String USER_NOT_EXISTS = "Credenciales inválidas";
    private final String ACCOUNT_CREATED = "Se ha creado la cuenta correctamente";
    private final String LINK_SENT = "Te hemos enviado un enlace para restablecer tu contraseña.";
    private final String EMAIL_NOT_EXISTS = "No existe ninguna cuenta con ese correo.";
    private final String RESET_PASSWORD = "La contraseña se ha cambiado correctamente";
    private final String PASSWORD_NOT_RESET = "La contraseña no se ha podido cambiar, intentálo de nuevo más tarde.";
    
    /**
     * Authenticate.
     *
     * @param authentication the authentication
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        clearCredentials();
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        jakarta.servlet.http.HttpSession session = attr.getRequest().getSession(true);
        
        HttpServletRequest request = attr.getRequest();
        String actionRegister = request.getParameter("actionRegister");
        String actionLogin = request.getParameter("actionLogin");
        String actionForgotPassword = request.getParameter("actionForgotPassword");
        String actionResetPassword = request.getParameter("actionResetPassword");
        
        register = "register".equals(actionRegister);
        login = "login".equals(actionLogin);
        forgotPassword = "forgot-password".equals(actionForgotPassword);
        resetPassword = "reset-password".equals(actionResetPassword);

        if (register && login) {
        	if(login(authentication, request, session)) {
	            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassw(), authorities);
        	} else {
        		throw new UserExistsException(USER_EXISTS);
        	}
        } else if(register && !login) {
        	if(login(authentication, request, session)) {
        		throw new AccountCreatedException(ACCOUNT_CREATED);
        	} else {
        		throw new UserExistsException(USER_EXISTS);
        	}
        } else if(!register && login) {
        	if(login(authentication, request, session)) {
	            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassw(), authorities);
        	} else {
        		throw new BadCredentialsException(USER_NOT_EXISTS);
        	}
        } else if(forgotPassword) {
        	if(forgotPassword(request, session)) {
        		throw new LinkSentSuccessfully(LINK_SENT);
        	} else {
        		throw new EmailDoesNotExist(EMAIL_NOT_EXISTS);
        	}
        } else if(resetPassword) {
    		if(resetPassword(request)) {
    			throw new ResetPasswordSuccessfully(RESET_PASSWORD);
        	} else {
        		throw new PasswordDoesNotReset(PASSWORD_NOT_RESET);
        	}
        }
        
        return null;
    }
    
    public boolean forgotPassword(HttpServletRequest request, jakarta.servlet.http.HttpSession session) {
    	String email = request.getParameter("email");
    	String token = getUserPortOut().forgotPassword(email);
    	
    	if(token != null) {
    		emailPortOut.sendResetPasswordEmail(email, token);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean resetPassword(HttpServletRequest request) {
    	Integer status = getUserPortOut().resetPassword(request.getParameter("newPass"), request.getParameter("token"));
    	if(status == 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean ifLogin(jakarta.servlet.http.HttpSession session) {
    	String userJson = getUserPortOut().load(user, session.getId(), register, login);
    	
    	if (userJson == null) {
            return false;
        }
    	try {
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue(userJson, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing user JSON", e);
        }
        System.out.println(user.getRolesStr());
        // Guardar en sesión
        session.setAttribute("userToken", user.getUserToken());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userRoles", user.getRolesStr());
        
        List<String> roles = Arrays.asList(user.getRolesStr().split("\\|"));
        user.setRoles(roles);
        
        // Agregar roles para que Spring Security lo reconozca
        if (authorities == null) authorities = new ArrayList<>();
        
        for (String r : user.getRoles()) {
        	authorities.add(() -> r.toUpperCase());
        }
    
        return true;
    }
    
    public void ifRegister(HttpServletRequest request) {
    	user.setName(request.getParameter("name"));
        user.setLastname1(request.getParameter("lastname1"));
        user.setLastname2(request.getParameter("lastname2"));
        user.setCity(request.getParameter("city"));
        user.setCountry(request.getParameter("country"));
        user.setAddress(request.getParameter("address"));
        user.setNumberAddress(request.getParameter("numberAddress"));
        user.setApartment(request.getParameter("apartment"));
        user.setZipCode(request.getParameter("zipCode"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
    }

    /**
     * Login.
     *
     * @param authentication the authentication
     * @return true, if successful
     */
    public boolean login(Authentication authentication, HttpServletRequest request, jakarta.servlet.http.HttpSession session) {
    	user = new User();
        
    	user.setUsername(authentication.getName());
        user.setPassw(authentication.getCredentials().toString());
        
        if(register && login) {
        	ifRegister(request);
        	
        	return ifLogin(session);
        } else if(register && !login) {
        	ifRegister(request);
        	
        	String userJson = getUserPortOut().load(user, session.getId(), register, login);
        	
        	if(userJson == null) {
        		return false;
        	} else {
        		return true;
        	}
        } else {
        	
        	return ifLogin(session);
        }
    }

    /**
     * Clear credentials.
     */
    public void clearCredentials() {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.clear();
    }

    /**
     * Supports.
     *
     * @param authentication the authentication
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

	public UserPortOut getUserPortOut() {
		return userPortOut;
	}

	public void setUserPortOut(UserPortOut userPortOut) {
		this.userPortOut = userPortOut;
	}
}
