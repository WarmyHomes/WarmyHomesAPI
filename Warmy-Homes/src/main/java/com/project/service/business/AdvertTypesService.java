package com.project.service.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.mappers.AdvertTypeMapper;
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
                       .map(advertTypeMapper::mapToAdvertTypeResponse)
                       .collect(Collectors.toList());


    }


    public AdvertTypeResponse getAdvertTypeById(Long id) {
        return null;

    }
}
