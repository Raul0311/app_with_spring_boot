package com.example.demo.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "mailing_address")
public class MailingAddressEntity {

	/**
     * Instantiates a new billing address entity.
     *
     * @param id the id
     * @param address the address
     * @param numberAddress the number address
     * @param apartment the apartment
     * @param city the city
     * @param zipCode the zip code
     * @param country the country
     * @param user_id the user id
     * @param predterminated billing address
     */
    public MailingAddressEntity(Long id, String address, String numberAddress, String apartment, String city,
			String zipCode, String country, UserEntity user_id, boolean predeterminated) {
		super();
		this.id = id;
		this.address = address;
		this.numberAddress = numberAddress;
		this.apartment = apartment;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.user_id = user_id;
		this.predeterminated = predeterminated;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "address", nullable = false)
    private String address;

    @Column(name = "number_address", nullable = false)
    private String numberAddress;

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "country", nullable = false)
    private String country;

    // Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user_id;
    
    @Column(name = "predeterminated", nullable = false)
    private boolean predeterminated;

    // Relación con órdenes de facturación (opcional)
    // @OneToMany(mappedBy = "billingAddress")
    // private List<Order> orders;
    
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
	 * @param username the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param username the new address
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
	 * @param username the new numberAddress
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
	 * @param username the new apartment
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
	 * @param username the new city
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
	 * @param username the new zipCode
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
	 * @param username the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the user_id.
	 *
	 * @return the user_id
	 */
	public UserEntity getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user_id.
	 *
	 * @param username the new user_id
	 */
	public void setUser_id(UserEntity user_id) {
		this.user_id = user_id;
	}
	
	/**
	 * Gets the predeterminated.
	 *
	 * @return the predeterminated
	 */
	public boolean getPredeterminated() {
		return predeterminated;
	}

	/**
	 * Sets the predeterminated.
	 *
	 * @param predeterminated the new predeterminated
	 */
	public void setPredeterminated(boolean predeterminated) {
		this.predeterminated = predeterminated;
	}
}
