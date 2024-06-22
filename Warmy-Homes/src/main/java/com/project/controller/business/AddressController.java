package com.project.controller.business;

import com.project.payload.response.business.adress.CityResponse;
import com.project.payload.response.business.adress.CountryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.adress.DistrictResponse;
import com.project.service.business.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService ;

    //*** getAllCountries()
    // http://localhost:8080/countries
    @GetMapping("/countries")
    public ResponseMessage<List<CountryResponse>> getAllCountries() {

        return addressService.getAllCountries();
    }

    //*** getAllCities()  U-02
    // http://localhost:8080/cities
    @GetMapping("/cities")
    public ResponseMessage<List<CityResponse>> getAllCities() {

        return addressService.getAllCities();
    }

    //*** getAllDistricts()
    // http://localhost:8080/districts
    @GetMapping("/districts")
    public ResponseMessage<List<DistrictResponse>> getAllDistricts() {

        return addressService.getAllDistricts();
    }
}
