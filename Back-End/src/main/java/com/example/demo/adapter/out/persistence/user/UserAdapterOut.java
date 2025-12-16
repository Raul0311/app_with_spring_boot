package com.example.demo.adapter.out.persistence.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

/**
 * The Class UserAdapterOut.
 */
@Service
public class UserAdapterOut implements UserPortOut {
	
	@SuppressWarnings("serial")
	public class UserNotFoundException extends RuntimeException {
	    public UserNotFoundException(String message) {
	        super(message);
	    }
	}
	
	@SuppressWarnings("serial")
	public class UserDisableException extends RuntimeException {
	    public UserDisableException(String message) {
	        super(message);
	    }
	}
	
	@SuppressWarnings("serial")
	public class UserUpdateException extends RuntimeException {
	    public UserUpdateException(String message) {
	        super(message);
	    }
	}

	private final UserRepository userRepository;

    public UserAdapterOut(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long validateUser(String userToken) {
        return userRepository.validateUserTokenByUserToken(userToken);
    }

    @Override
    public User load(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con id: " + userId));
        userEntity.setPassw(null);

        return UserMapper.toDomain(userEntity);
    }

	@Override
	public User update(User user) {
		Long userId = user.getId();
		
		if (!userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
		UserEntity userEntity = UserMapper.toEntity(user);
		
		Integer result = userRepository.updateUser(userEntity.getUsername(), userEntity.getName(), userEntity.getLastname1(), 
				userEntity.getLastname2(), userEntity.getEmail(), userEntity.getPhone(), userEntity.getAddress(), userEntity.getNumberAddress(), 
				userEntity.getApartment(), userEntity.getCity(), userEntity.getZipCode(), userEntity.getCountry(), userEntity.getId());
		
        if (result == 0) throw new UserUpdateException("No se pudo actualizar el perfil del usuario con ID: " + user.getId());
        
        UserEntity newUserEntity = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User updated but not found for fetching"));
        return UserMapper.toDomain(newUserEntity);
	}

	@Override
	public void disableUser(Long userId) {
		Integer updated = userRepository.disableUser(userId);
		if (updated == 0) throw new UserDisableException("No se ha podido eliminar la cuenta");
	}
}