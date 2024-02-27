package com.project.service.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertTypeMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.AdvertTypeResponse;
import com.project.repository.business.AdvertTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypesService {

    private final AdvertTypesRepository advertTypesRepository;
    private final AdvertTypeMapper advertTypeMapper;


    public List<AdvertTypeResponse> getAllAdvertTypes() {
        // Tüm advert tiplerini veritabanından al
        List<Advert_Type> advertTypes = advertTypesRepository.findAll();

        // AdvertType nesnelerini AdvertTypeResponse nesnelerine dönüştür

             return     advertTypes
                       .stream()
                       .map(advertTypeMapper::mapAdverTypeToAdvertTypeResponse)
                       .collect(Collectors.toList());


    }


    public AdvertTypeResponse getAdvertTypeById(Long id) {

        Advert_Type advert=isAdvertTaypExist(id);
        return advertTypeMapper.mapAdverTypeToAdvertTypeResponse(advert);


    }


    public Advert_Type isAdvertTaypExist(Long id){
        return advertTypesRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id)));
    }


}
