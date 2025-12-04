package com.example.demo.adapter.out.persistence;

import org.springframework.stereotype.Service;

import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

import jakarta.transaction.Transactional;

/**
 * The Class UserService.
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
	public User load(User user, String session_id, boolean register, boolean login) {
		UserDTO userDTO = null;
		try {
			if(register && login) {
				userDTO = userRepository.loginAndRegister(user.getUsername(), user.getPassw(), user.getName(), 
						user.getLastname1(), user.getLastname2(), user.getCity(), user.getCountry(), user.getAddress(), 
						user.getNumberAddress(), user.getApartment(), user.getZipCode(), user.getPhone(), 
						user.getEmail(), session_id, register, login);
			} else if(register && !login) {
				userDTO = userRepository.loginAndRegister(user.getUsername(), user.getPassw(), user.getName(), 
						user.getLastname1(), user.getLastname2(), user.getCity(), user.getCountry(), user.getAddress(), 
						user.getNumberAddress(), user.getApartment(), user.getZipCode(), user.getPhone(), 
						user.getEmail(), session_id, register, login);
			} else if(!register && login) {
				userDTO = userRepository.loginAndRegister(user.getUsername(), user.getPassw(), 
						null, null, null, null, null, null, null, null, null, null, null, session_id, register, login);
			}
			
			if (userDTO != null) {
                userDTO.setPassw(null);
            }
		} catch (Exception e) {
		    throw new RuntimeException("DB error: " + e.getMessage(), e);
		}

		if (userDTO == null || userDTO.getId() == null) {
            return null; 
        }

	    return UserMapper.DTOtoDomain(userDTO);
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
}