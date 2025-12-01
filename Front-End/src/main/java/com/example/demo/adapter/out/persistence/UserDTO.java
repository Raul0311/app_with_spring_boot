package com.example.demo.adapter.out.persistence;

import java.time.LocalDateTime;

/**
 * The DTO that represents a user returned from stored procedures.
 * It has the same structure as UserEntity but without JPA annotations.
 */
public class UserDTO {
    
	/**
     * Instantiates a new user DTO with all fields.
     *
     * @param id the user id
     * @param username the username
     * @param passw the password
     * @param name the first name
     * @param lastname1 the first lastname
     * @param lastname2 the second lastname
     * @param city the city
     * @param country the country
     * @param address the address
     * @param numberAddress the number of the address
     * @param zipCode the zip code
     * @param phone the phone number
     * @param email the email
     * @param enabled whether the user is enabled
     * @param lastLogin last login timestamp
     * @param createdAt creation timestamp
     * @param updatedAt last update timestamp
     * @param userToken the user token
     * @param rolesStr roles string
     */
    public UserDTO(Long id, String username, String passw, String name, String lastname1, String lastname2,
            String city, String country, String address, String numberAddress, String apartment, String zipCode, String phone,
            String email, Boolean enabled, LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedAt,
            String userToken, String rolesStr) {
        super();
        this.id = id;
        this.username = username;
        this.passw = passw;
        this.name = name;
        this.lastname1 = lastname1;
        this.lastname2 = lastname2;
        this.city = city;
        this.country = country;
        this.address = address;
        this.numberAddress = numberAddress;
        this.apartment = apartment;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.enabled = enabled;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userToken = userToken;
        this.rolesStr = rolesStr;
    }

    /**
	 * Instantiates a new user DTO.
	 */
    public UserDTO() {}

    private Long id;
    private String username;
    private String passw;
    private String name;
    private String lastname1;
    private String lastname2;
    private String city;
    private String country;
    private String address;
    private String numberAddress;
    private String apartment;
    private String zipCode;
    private String phone;
    private String email;
    private Boolean enabled;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userToken;
    private String rolesStr;
    
    // -------------------
    // GETTERS & SETTERS
    // -------------------

    /**
	 * Gets the id.
	 *
	 * @return the id
	 */
    public Long getId() {
        return id;
    }

    /**
	 * Gets the username.
	 *
	 * @return the username
	 */
    public String getUsername() {
        return username;
    }

    /**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
	 * Gets the password.
	 *
	 * @return the password
	 */
    public String getPassw() {
        return passw;
    }

    /**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
    public void setPassw(String password) {
        this.passw = password;
    }

    /**
	 * Gets the name.
	 *
	 * @return the name
	 */
    public String getName() {
        return name;
    }

    /**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
    public void setName(String name) {
        this.name = name;
    }

    /**
	 * Gets the first lastname.
	 *
	 * @return the first lastname
	 */
    public String getLastname1() {
        return lastname1;
    }

    /**
	 * Sets the first lastname.
	 *
	 * @param lastname1 the new first lastname
	 */
    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    /**
	 * Gets the second lastname.
	 *
	 * @return the second lastname
	 */
    public String getLastname2() {
        return lastname2;
    }

    /**
	 * Sets the second lastname.
	 *
	 * @param lastname2 the new second lastname
	 */
    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    /**
	 * Gets the city.
	 *
	 * @return the city
	 */
    public String getCity() {
        return city;
    }

    /**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
	 * Gets the country.
	 *
	 * @return the country
	 */
    public String getCountry() {
        return country;
    }

    /**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
	 * Gets the address.
	 *
	 * @return the address
	 */
    public String getAddress() {
        return address;
    }

    /**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
	 * Gets the number address.
	 *
	 * @return the number address
	 */
    public String getNumberAddress() {
        return numberAddress;
    }

    /**
	 * Sets the number address.
	 *
	 * @param numberAddress the new number address
	 */
    public void setNumberAddress(String numberAddress) {
        this.numberAddress = numberAddress;
    }
    
    /**
	 * Gets the apartment.
	 *
	 * @return the apartment
	 */
	public String getApartment() {
		return apartment;
	}

	/**
	 * Sets the apartment.
	 *
	 * @param apartment the new apartment
	 */
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

    /**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
    public String getZipCode() {
        return zipCode;
    }

    /**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
    public String getPhone() {
        return phone;
    }

    /**
	 * Sets the phone number.
	 *
	 * @param phone the new phone number
	 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
	 * Gets the email.
	 *
	 * @return the email
	 */
    public String getEmail() {
        return email;
    }

    /**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
	 * Gets whether the user is enabled.
	 *
	 * @return enabled
	 */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
	 * Sets whether the user is enabled.
	 *
	 * @param enabled the new enabled value
	 */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
	 * Gets last login.
	 *
	 * @return the last login
	 */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
	 * Sets last login.
	 *
	 * @param lastLogin new last login
	 */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
	 * Gets creation.
	 *
	 * @return the created_at
	 */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
	 * Sets creation.
	 *
	 * @param createdAt creation
	 */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
	 * Gets last update.
	 *
	 * @return the updated_at
	 */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
	 * Sets last update.
	 *
	 * @param updatedAt update
	 */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** 
     * Gets the user token. 
     * 
     * @return the userToken
     * */
    public String getUserToken() { 
    	return userToken; 
	}

    /** 
     * Sets the user token. 
     * 
     * @param the userToken
     * */
    public void setUserToken(String userToken) { 
    	this.userToken = userToken; 
	}

    /** 
     * Gets the user roles. 
     * 
     * @return the rolesStr
     * */
    public String getRolesStr() { 
    	return rolesStr; 
	}

    /** 
     * Sets the user roles. 
     * 
     * @param the rolesStr
     * */
    public void setRolesStr(String rolesStr) { 
    	this.rolesStr = rolesStr; 
	}
}
