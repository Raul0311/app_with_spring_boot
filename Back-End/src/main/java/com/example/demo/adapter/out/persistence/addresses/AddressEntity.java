package com.example.demo.adapter.out.persistence.addresses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "addresses")
public class AddressEntity {
    
    public enum AddressType {
        BILLING,
        SHIPPING
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Enumerated(EnumType.STRING)
    private AddressType type;
	
	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "lastname1", nullable = false)
    private String lastname1;
	
	@Column(name = "lastname2", nullable = false)
    private String lastname2;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "predeterminated")
    private Boolean predeterminated;

    // Relación con órdenes de facturación (opcional)
    // @OneToMany(mappedBy = "billingAddress")
    // private List<Order> orders;
}
