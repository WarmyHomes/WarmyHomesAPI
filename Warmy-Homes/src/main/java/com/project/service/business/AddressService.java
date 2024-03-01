package com.project.service.business;

import com.project.payload.mappers.AddressMapper;
import com.project.payload.response.business.AddressResponse;
import com.project.payload.response.business.ResponseMessage;
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
    public ResponseMessage<List<AddressResponse>> getAllCountries() {

        List<AddressResponse> response =  countryRepository.findAll()
                                                 .stream()
                                                 .map(addressMapper::mapAddressCountryToAddressResponse)
                                                 .collect(Collectors.toList());

        return ResponseMessage.<List<AddressResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    // getAllCities()*******
    public ResponseMessage<List<AddressResponse>> getAllCities() {
        List<AddressResponse> response =  cityRepository.findAll()
                .stream()
                .map(addressMapper::mapAddressCityToAddressResponse)
                .collect(Collectors.toList());

        return ResponseMessage.<List<AddressResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    public ResponseMessage<List<AddressResponse>> getAllDistricts() {
        List<AddressResponse> response =  districtRepository.findAll()
                .stream()
                .map(addressMapper::mapAddressDistrictToAddressResponse)
                .collect(Collectors.toList());

        return ResponseMessage.<List<AddressResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }
}
