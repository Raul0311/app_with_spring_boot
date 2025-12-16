package com.example.demo.domain;

import com.example.demo.adapter.out.persistence.addresses.AddressEntity.AddressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

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
}
