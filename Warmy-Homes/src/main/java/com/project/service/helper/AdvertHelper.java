package com.project.service.helper;

import com.project.entity.business.*;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.business.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertHelper {
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;
    private final AdvertTypesRepository advertTypesRepository;
    private final AddressCountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressDistrictRepository districtRepository;
    public Advert isExistSlug(String slug){
        return advertRepository.findBySlug(slug).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }

    // ****************HELPER METHODE*************
    public   Advert isAdvertExist(Long id){
        return advertRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }

    public Category isCategoryExist(Long id){

        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));
    }
    public Advert_Type isAdvert_TypeExist(Long id){

        return advertTypesRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ADVERT_TYPE_NOT_FOUND));
    }
    public Country isCountryExist(Long id){

        return countryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Country not found."));
    }
    public City isCityExist(Long id){

        return cityRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("City not found."));
    }
    public District isDistrictExist(Long id){

        return districtRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("District not found."));
    }


}
