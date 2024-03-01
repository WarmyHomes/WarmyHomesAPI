package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.abstracts.AbstractAdvertRequest;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import com.project.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final UserRoleService userRoleService;
    //A10
    public ResponseMessage<AdvertResponse> saveAdvert(Long id, BaseAdvertRequest advertRequest) {

        //! Ayni id var mı?
        Advert advert = isAdvertExist(id);

        // ! isBuiltin
        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }else {
            Advert advertMap = advertMapper.mapAdvertRequestToAdvert(advertRequest);
            Advert savedAdvert = advertRepository.save(advertMap);
        }

        AdvertResponse advertResponse = advertMapper.mapAdvertToAdvertResponse(advert);
        //isAdvertExistByAdvertSlug(AbstractAdvertRequest)

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertResponse)
                .message(SuccessMessages.ADVERT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
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

        return advertRepository.getCityByAdvert();
    }


    public ResponseEntity<AdvertResponse> deleteAdvertById(Long advertId) {

        Advert advert = isAdvertExist(advertId);
        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_IS_BULT_IN);
        }

        advertRepository.deleteById(advertId);
        AdvertResponse advertResponse = advertMapper.mapAdvertToAdvertResponse(advert);

        return null;
    }

    private  Advert isAdvertExist(Long id){
        return advertRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }
}
