package com.example.demo.adapter.out.persistence.user;

import com.example.demo.domain.User;

/**
 * Mapper between UserEntity, UserDTO and User domain object.
 */
public class UserMapper {

    /**
     * Entity to domain.
     *
     * @param userEntity the user entity
     * @return the user domain object
     */
    public static User toDomain(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        user.setPassw(userEntity.getPassw());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        user.setLastname1(userEntity.getLastname1());
        user.setLastname2(userEntity.getLastname2());
        user.setCity(userEntity.getCity());
        user.setCountry(userEntity.getCountry());
        user.setAddress(userEntity.getAddress());
        user.setNumberAddress(userEntity.getNumberAddress());
        user.setApartment(userEntity.getApartment());
        user.setZipCode(userEntity.getZipCode());
        user.setPhone(userEntity.getPhone());
        user.setEnabled(userEntity.getEnabled());
        user.setLastLogin(userEntity.getLastLogin());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setUpdatedAt(userEntity.getUpdatedAt());

        return user;
    }

    /**
     * Domain to entity.
     *
     * @param user the user domain object
     * @return the user entity
     */
    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassw(user.getPassw());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setLastname1(user.getLastname1());
        userEntity.setLastname2(user.getLastname2());
        userEntity.setCity(user.getCity());
        userEntity.setCountry(user.getCountry());
        userEntity.setAddress(user.getAddress());
        userEntity.setNumberAddress(user.getNumberAddress());
        userEntity.setApartment(user.getApartment());
        userEntity.setZipCode(user.getZipCode());
        userEntity.setPhone(user.getPhone());
        userEntity.setEnabled(user.getEnabled());
        userEntity.setLastLogin(user.getLastLogin());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());

        return userEntity;
    }
}
