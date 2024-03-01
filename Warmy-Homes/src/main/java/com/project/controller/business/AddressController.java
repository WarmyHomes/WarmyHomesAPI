package com.project.controller.business;

import com.project.payload.response.business.AddressResponse;
import com.project.payload.response.business.ResponseMessage;
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
    public ResponseMessage<List<AddressResponse>> getAllCountries() {

        return addressService.getAllCountries();
    }

    //*** getAllCities()
    // http://localhost:8080/cities
    @GetMapping("/cities")
    public ResponseMessage<List<AddressResponse>> getAllCities() {

        return addressService.getAllCities();
    }

    //*** getAllDistricts()
    // http://localhost:8080/districts
    @GetMapping("/districts")
    public ResponseMessage<List<AddressResponse>> getAllDistricts() {

        return addressService.getAllDistricts();
    }
}
