package com.example.demo.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	/**
	 * Validate the userToken.
	 *
	 * @param userId the user id
	 * @param userToken the user token
	 * @return if the userToken is validate
	 */
	@Query(value="SELECT CASE WHEN EXISTS(SELECT 1 FROM user_tokens WHERE user_id = ?1 AND user_token = ?2) THEN TRUE ELSE FALSE END", nativeQuery = true)
	Integer validateUserTokenByUserIdAndUserToken(Long userId, String userToken);
}