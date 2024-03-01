package com.project.service.business;

import com.project.exception.ConflictException;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.abstracts.AbstractAdvertRequest;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    //A10
    public ResponseMessage<AdvertResponse> saveAdvert(String userRole, BaseAdvertRequest advertRequest) {

        //isAdvertExistByAdvertSlug(AbstractAdvertRequest)
        advertRepository.save(advertMapper.mapAdvertRequestToAdvert(advertRequest));
        return null;
    }
    private boolean isAdvertExistByAdvertSlug(String slug){

        boolean advertExist = advertRepository.existsAdvertBySlug(slug);
        if (advertExist){
            throw  new ConflictException(ErrorMessages.ADVERT_ALREADY_EXIST);
        }else {
            return false;
        }
    }

    //A01
    public Page<AdvertResponse> allAdvertsByPage(int page, int size, String sort, String type, String userRole, AbstractAdvertRequest advertRequest) {

        return null;
    }

    //A02

    public ResponseEntity<Array<CityResponse>> getCityByAdvert(String request) {

        return advertRepository.getCityByAdvert(request);
    }
}
