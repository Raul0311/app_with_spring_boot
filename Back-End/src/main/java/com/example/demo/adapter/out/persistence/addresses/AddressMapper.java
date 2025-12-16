package com.example.demo.adapter.out.persistence.addresses;

import com.example.demo.domain.Address;

public class AddressMapper {

    /**
     * Convierte una AddressEntity (persistencia) a Address (dominio)
     */
    public static Address toDomain(AddressEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Address(
                entity.getId(),
                entity.getType(),
                entity.getName(),
                entity.getLastname1(),
                entity.getLastname2(),
                entity.getAddress(),
                entity.getNumberAddress(),
                entity.getApartment(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getCountry(),
                entity.getUserId(),
                entity.getPredeterminated()
        );
    }

    /**
     * Convierte una Address (dominio) a AddressEntity (persistencia)
     */
    public static AddressEntity toEntity(Address domain) {
        if (domain == null) {
            return null;
        }

        return new AddressEntity(
                domain.getId(),
                domain.getType(),
                domain.getName(),
                domain.getLastname1(),
                domain.getLastname2(),
                domain.getAddress(),
                domain.getNumberAddress(),
                domain.getApartment(),
                domain.getCity(),
                domain.getZipCode(),
                domain.getCountry(),
                domain.getUserId(),
                domain.getPredeterminated()
        );
    }
}
