package com.example.demo.adapter.out.persistence;

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


	private final UserRepository userRepository;

    public UserAdapterOut(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateUser(Long userId, String userToken) {
        Integer result = userRepository.validateUserTokenByUserIdAndUserToken(userId, userToken);
        boolean valid = result != null && result == 1;
        if (!valid) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user token");
    }

    @Override
    public User load(Long userId, String userToken) {
        validateUser(userId, userToken);

        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con id: " + userId));

        return UserMapper.toDomain(userEntity);
    }

	@Override
	public User update(User user, String userToken) {
		validateUser(user.getId(), userToken);
		if (!userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
		
		return UserMapper.toDomain(userRepository.save(UserMapper.toEntity(user)));
	}

}