package com.project.payload.mappers;

import com.project.entity.business.City;
import com.project.entity.business.Country;
import com.project.entity.business.District;
import com.project.payload.response.business.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // POJO --> DTO
    public AddressResponse mapAddressCountryToAddressResponse(Country country){

        return AddressResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    // POJO --> DTO
    public AddressResponse mapAddressCityToAddressResponse(City city){

        return AddressResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

    // POJO --> DTO
    public AddressResponse mapAddressDistrictToAddressResponse(District district){

        return AddressResponse.builder()
                .id(district.getId())
                .name(district.getName())
                .build();
    }
}
