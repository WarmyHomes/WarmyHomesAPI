package com.project.service.business;

import com.project.payload.mappers.AddressMapper;
import com.project.payload.response.business.adress.CityResponse;
import com.project.payload.response.business.adress.CountryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.adress.DistrictResponse;
import com.project.repository.business.AddressCityRepository;
import com.project.repository.business.AddressCountryRepository;
import com.project.repository.business.AddressDistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressCountryRepository countryRepository;

    private final AddressCityRepository cityRepository;

    private final AddressDistrictRepository districtRepository;

    private final AddressMapper addressMapper;


    // getAllCountries()*******
    public ResponseMessage<List<CountryResponse>> getAllCountries() {

        List<CountryResponse> response =  countryRepository.findAll()
                                                 .stream()
                                                 .map(addressMapper::mapAddressCountryToAddressResponse)
                                                 .collect(Collectors.toList());

        return ResponseMessage.<List<CountryResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    // getAllCities()*******
    public ResponseMessage<List<CityResponse>> getAllCities() {
        List<CityResponse> response =  cityRepository.findAll()
                .stream()
                .map(addressMapper::mapAddressCityToAddressResponse)
                .collect(Collectors.toList());

        return ResponseMessage.<List<CityResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    public ResponseMessage<List<DistrictResponse>> getAllDistricts() {
        List<DistrictResponse> response =  districtRepository.findAll()
                .stream()
                .map(addressMapper::mapAddressDistrictToAddressResponse)
                .collect(Collectors.toList());

        return ResponseMessage.<List<DistrictResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }
}
