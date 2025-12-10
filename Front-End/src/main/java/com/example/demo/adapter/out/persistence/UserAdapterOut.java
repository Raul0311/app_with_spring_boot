package com.example.demo.adapter.out.persistence;

import org.springframework.stereotype.Service;

import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

import jakarta.transaction.Transactional;

/**
 * The Class UserAdapterOut.
 */
@Service
public class UserAdapterOut implements UserPortOut {

	private final UserRepository userRepository;

    public UserAdapterOut(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	/**
	 * Load.
	 *
	 * @param user
	 * @param session_id the session_id
	 * @param register the register
	 * @return the user
	 */
	@Override
	public String load(User user, String session_id, boolean register, boolean login) {
		String userData = null;
	    // UserDTO userDTO = null;
	    
	    try {
	        // Llamada al SP
	        if (register && login) {
	        	userData = userRepository.loginAndRegister(user.getUsername(), user.getPassw(), user.getName(), 
	                    user.getLastname1(), user.getLastname2(), user.getCity(), user.getCountry(), user.getAddress(), 
	                    user.getNumberAddress(), user.getApartment(), user.getZipCode(), user.getPhone(), 
	                    user.getEmail(), session_id, register, login);
	        } else if (register && !login) {
	        	userData = userRepository.loginAndRegister(user.getUsername(), user.getPassw(), user.getName(), 
	                    user.getLastname1(), user.getLastname2(), user.getCity(), user.getCountry(), user.getAddress(), 
	                    user.getNumberAddress(), user.getApartment(), user.getZipCode(), user.getPhone(), 
	                    user.getEmail(), session_id, register, login);
	        } else if (!register && login) {
	        	userData = userRepository.login(user.getUsername(), user.getPassw(), session_id);
	        }
	        
	        // || userDTO.getId() == null
	        if (userData == null) {
		        return null;
		    }
            
            // Siempre poner la contraseña a null antes de devolver
	        /*if (userDTO != null) {
	            userDTO.setPassw(null);
	        }*/

	    } catch (Exception e) {
	        throw new RuntimeException("DB error: " + e.getMessage(), e);
	    }

	    //UserMapper.DTOtoDomain(userDTO)
	    return userData;
	}
	
	/**
	 * deleteUserToken.
	 *
	 * @param id_session the id_session
	 */
	@Override
	@Transactional
	public void deleteUserToken(String id_session) {
		try {
			userRepository.deleteUserToken(id_session);
		} catch (Exception e) {
			throw new RuntimeException("Internal server error deleting user token", e);
		}
	}

	/**
	 * forgotPassword.
	 *
	 * @param email the email
	 */
	@Override
	public String forgotPassword(String email) {
		try {
			return userRepository.forgotPassword(email);
		} catch (Exception e) {
			throw new RuntimeException("Internal server error sending email", e);
		}
	}

	/**
	 * resetPassword.
	 *
	 * @param newPass the newPass
	 */
	@Override
	public Integer resetPassword(String newPass, String token) {
		try {
			return userRepository.resetPassword(newPass, token);
		} catch (Exception e) {
			throw new RuntimeException("Internal server error resetting password", e);
		}
	}
}