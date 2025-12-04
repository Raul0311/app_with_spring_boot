package com.example.demo.domain;

import com.example.demo.adapter.out.persistence.AddressEntity.AddressType;

public class Address {

	/**
     * Instantiates a new billing address entity.
     *
     * @param id the id
     * @param name the name
     * @param lastname1 the lastname1
     * @param lastname2 the lastname2
     * @param address the address
     * @param numberAddress the number address
     * @param apartment the apartment
     * @param city the city
     * @param zipCode the zip code
     * @param country the country
     * @param userId the user id
     * @param predterminated billing address
     */
    public Address(Long id, AddressType type, String name, String lastname1, String lastname2, String address, String numberAddress, String apartment, String city,
			String zipCode, String country, Long userId, Boolean predeterminated) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.lastname1 = lastname1;
		this.lastname2 = lastname2;
		this.address = address;
		this.numberAddress = numberAddress;
		this.apartment = apartment;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.userId = userId;
		this.predeterminated = predeterminated;
	}

    private Long id;
    
    private AddressType type;
    
    private String name;
    
    private String lastname1;
    
    private String lastname2;

    private String address;

    private String numberAddress;

    private String apartment;

    private String city;

    private String zipCode;

    private String country;

    private Long userId;
    
    private Boolean predeterminated;
    
    /**
	 * Gets the id.
	 *
	 * @return the id
	 */
    public Long getId() {
		return id;
	}

    /**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
     * Gets the type.
     *
     * @return the type
     */
    public AddressType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(AddressType type) {
        this.type = type;
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
     * Gets the lastname1.
     *
     * @return the lastname1
     */
    public String getLastname1() {
        return lastname1;
    }

    /**
     * Sets the lastname1.
     *
     * @param lastname1 the new lastname1
     */
    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    /**
     * Gets the lastname2.
     *
     * @return the lastname2
     */
    public String getLastname2() {
        return lastname2;
    }

    /**
     * Sets the lastname2.
     *
     * @param lastname2 the new lastname2
     */
    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
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
	 * Gets the numberAddress.
	 *
	 * @return the numberAddress
	 */
	public String getNumberAddress() {
		return numberAddress;
	}

	/**
	 * Sets the numberAddress.
	 *
	 * @param numberAddress the new numberAddress
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
	 * Gets the zipCode.
	 *
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zipCode.
	 *
	 * @param zipCode the new zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	 * Gets the userId.
	 *
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 *
	 * @param userId the new userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the predeterminated.
	 *
	 * @return the predeterminated
	 */
	public Boolean getPredeterminated() {
		return predeterminated;
	}

	/**
	 * Sets the predeterminated.
	 *
	 * @param predeterminated the new predeterminated
	 */
	public void setPredeterminated(Boolean predeterminated) {
		this.predeterminated = predeterminated;
	}
}
