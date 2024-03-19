package com.project.service.helper;

import com.project.entity.business.Advert;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.business.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertHelper {
    private final AdvertRepository advertRepository;
    public Advert isExistSlug(String slug){
        return advertRepository.findBySlug(slug).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }

    // ****************HELPER METHODE*************
    public   Advert isAdvertExist(Long id){
        return advertRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }
}
