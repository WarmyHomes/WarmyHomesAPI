package com.project.payload.mappers;

import com.project.entity.business.City;
import com.project.entity.business.Country;
import com.project.entity.business.District;
import com.project.payload.response.business.adress.CityResponse;
import com.project.payload.response.business.adress.CountryResponse;
import com.project.payload.response.business.adress.DistrictResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // POJO --> DTO
    public CountryResponse mapAddressCountryToAddressResponse(Country country){

        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    // POJO --> DTO
    public CityResponse mapAddressCityToAddressResponse(City city){

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .country_id(city.getCountry().getId())
                .build();
    }

    // POJO --> DTO
    public DistrictResponse mapAddressDistrictToAddressResponse(District district){

        return DistrictResponse.builder()
                .id(district.getId())
                .name(district.getName())
                .city_id(district.getCity().getId())
                .build();
    }
}
