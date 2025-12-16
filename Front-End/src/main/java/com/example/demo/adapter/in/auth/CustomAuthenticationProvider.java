package com.example.demo.adapter.in.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.application.ports.out.EmailPortOut;
import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * The Class CustomAuthenticationProvider.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	/** The Constant USER_EXISTS. */
	private static final String USER_EXISTS = "El usuario ya existe";
    
    /** The Constant USER_NOT_EXISTS. */
    private static final String USER_NOT_EXISTS = "Credenciales inválidas";
    
    /** The Constant ACCOUNT_CREATED. */
    private static final String ACCOUNT_CREATED = "Se ha creado la cuenta correctamente";
    
    /** The Constant LINK_SENT. */
    private static final String LINK_SENT = "Te hemos enviado un enlace para restablecer tu contraseña.";
    
    /** The Constant EMAIL_NOT_EXISTS. */
    private static final String EMAIL_NOT_EXISTS = "No existe ninguna cuenta con ese correo.";
    
    /** The Constant RESET_PASSWORD. */
    private static final String RESET_PASSWORD = "La contraseña se ha cambiado correctamente";
    
    /** The Constant PASSWORD_NOT_RESET. */
    private static final String PASSWORD_NOT_RESET = "La contraseña no se ha podido cambiar, intentálo de nuevo más tarde.";
    
    /** The user port out. */
    private UserPortOut userPortOut;
    
    /** The email port out. */
    private EmailPortOut emailPortOut;
    
    /**
     * Instantiates a new custom authentication provider.
     *
     * @param userPortOut the user port out
     * @param emailPortOut the email port out
     */
    public CustomAuthenticationProvider(UserPortOut userPortOut, EmailPortOut emailPortOut) {
    	this.userPortOut = userPortOut;
    	this.emailPortOut = emailPortOut;
    }
    
    /**
     * Authenticate.
     *
     * @param authentication the authentication
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        jakarta.servlet.http.HttpSession session = attr.getRequest().getSession(true);
        
        boolean isRegister = "register".equals(request.getParameter("actionRegister"));
        boolean isLogin = "login".equals(request.getParameter("actionLogin"));
        
        if ("forgot-password".equals(request.getParameter("actionForgotPassword"))) {
            return handleForgotPassword(request);
        }

        if ("reset-password".equals(request.getParameter("actionResetPassword"))) {
            return handleResetPassword(request);
        }
        
        return processAuthentication(authentication, request, session, isRegister, isLogin);
    }
    
    /**
     * Process authentication.
     *
     * @param auth the auth
     * @param req the req
     * @param session the session
     * @param reg the reg
     * @param log the log
     * @return the authentication
     */
    private Authentication processAuthentication(Authentication auth, HttpServletRequest req, HttpSession session, boolean reg, boolean log) {
        // Validar null para evitar NullPointerException (Aviso SonarQube)
        Object credentials = auth.getCredentials();
        String password = (credentials != null) ? credentials.toString() : "";

        User user = new User();
        user.setUsername(auth.getName());
        user.setPassw(password);

        if (reg) {
            fillRegistrationData(user, req);
        }

        // Caso: Registro sin Login inmediato
        if (reg && !log) {
            if (userPortOut.load(user, session.getId(), true, false) != null) {
                throw new AccountCreatedException(ACCOUNT_CREATED);
            }
            throw new UserExistsException(USER_EXISTS);
        }

        // Caso: Login (puro o tras registro)
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (attemptLogin(user, session, reg, log, authorities)) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassw(), authorities);
        }

        throw new BadCredentialsException(reg ? USER_EXISTS : USER_NOT_EXISTS);
    }
    
    /**
     * Attempt login.
     *
     * @param user the user
     * @param session the session
     * @param reg the reg
     * @param log the log
     * @param authorities the authorities
     * @return true, if successful
     */
    private boolean attemptLogin(User user, HttpSession session, boolean reg, boolean log, List<GrantedAuthority> authorities) {
        String userJson = userPortOut.load(user, session.getId(), reg, log);
        if (userJson == null) return false;

        try {
            ObjectMapper mapper = new ObjectMapper();
            User data = mapper.readValue(userJson, User.class);
            
            // Actualizar el objeto user con los datos del JSON
            user.setId(data.getId());
            user.setUserToken(data.getUserToken());
            
            session.setAttribute("userToken", data.getUserToken());
            session.setAttribute("userRoles", data.getRolesStr());

            if (data.getRolesStr() != null && !data.getRolesStr().isEmpty()) {
                Arrays.stream(data.getRolesStr().split("\\|"))
                    .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toUpperCase())));
            }
            return true;
        } catch (Exception e) {
            // Excepción específica (Aviso SonarQube)
            throw new DatabaseOperationException("Error procesando JSON de usuario", e);
        }
    }

    /**
     * Fill registration data.
     *
     * @param user the user
     * @param request the request
     */
    private void fillRegistrationData(User user, HttpServletRequest request) {
        user.setName(request.getParameter("name"));
        user.setLastname1(request.getParameter("lastname1"));
        user.setLastname2(request.getParameter("lastname2"));
        user.setStreet(request.getParameter("street"));
        user.setCity(request.getParameter("city"));
        user.setCountry(request.getParameter("country"));
        user.setNumberAddress(request.getParameter("numberAddress"));
        user.setApartment(request.getParameter("apartment"));
        user.setZipCode(request.getParameter("zipCode"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
    }

    /**
     * Handle forgot password.
     *
     * @param request the request
     * @return the authentication
     */
    // Métodos de apoyo para reducir complejidad cognitiva
    private Authentication handleForgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = userPortOut.forgotPassword(email);
        if (token != null) {
            emailPortOut.sendResetPasswordEmail(email, token);
            throw new LinkSentSuccessfully(LINK_SENT);
        }
        throw new EmailDoesNotExist(EMAIL_NOT_EXISTS);
    }

    /**
     * Handle reset password.
     *
     * @param request the request
     * @return the authentication
     */
    private Authentication handleResetPassword(HttpServletRequest request) {
        Integer status = userPortOut.resetPassword(request.getParameter("newPass"), request.getParameter("token"));
        if (status != null && status == 1) {
            throw new ResetPasswordSuccessfully(RESET_PASSWORD);
        }
        throw new PasswordDoesNotReset(PASSWORD_NOT_RESET);
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

	/**
	 * Gets the user port out.
	 *
	 * @return the user port out
	 */
	public UserPortOut getUserPortOut() {
		return userPortOut;
	}

	/**
	 * Sets the user port out.
	 *
	 * @param userPortOut the new user port out
	 */
	public void setUserPortOut(UserPortOut userPortOut) {
		this.userPortOut = userPortOut;
	}
	
	/**
	 * The Class AccountCreatedException.
	 */
	@SuppressWarnings("serial")
	public static class AccountCreatedException extends AuthenticationException {
        
        /**
         * Instantiates a new account created exception.
         *
         * @param msg the msg
         */
        public AccountCreatedException(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class UserExistsException.
     */
    @SuppressWarnings("serial")
    public static class UserExistsException extends AuthenticationException {
        
        /**
         * Instantiates a new user exists exception.
         *
         * @param msg the msg
         */
        public UserExistsException(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class LinkSentSuccessfully.
     */
    @SuppressWarnings("serial")
    public static class LinkSentSuccessfully extends AuthenticationException {
        
        /**
         * Instantiates a new link sent successfully.
         *
         * @param msg the msg
         */
        public LinkSentSuccessfully(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class EmailDoesNotExist.
     */
    @SuppressWarnings("serial")
    public static class EmailDoesNotExist extends AuthenticationException {
        
        /**
         * Instantiates a new email does not exist.
         *
         * @param msg the msg
         */
        public EmailDoesNotExist(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class ResetPasswordSuccessfully.
     */
    @SuppressWarnings("serial")
    public static class ResetPasswordSuccessfully extends AuthenticationException {
        
        /**
         * Instantiates a new reset password successfully.
         *
         * @param msg the msg
         */
        public ResetPasswordSuccessfully(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class PasswordDoesNotReset.
     */
    @SuppressWarnings("serial")
    public static class PasswordDoesNotReset extends AuthenticationException {
        
        /**
         * Instantiates a new password does not reset.
         *
         * @param msg the msg
         */
        public PasswordDoesNotReset(String msg) {
            super(msg);
        }
    }
    
    /**
     * The Class DatabaseOperationException.
     */
    @SuppressWarnings("serial")
	public class DatabaseOperationException extends RuntimeException {
	    
    	/**
    	 * Instantiates a new database operation exception.
    	 *
    	 * @param message the message
    	 * @param cause the cause
    	 */
    	public DatabaseOperationException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
}
