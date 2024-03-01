package com.project.service.business;

import com.project.entity.business.Tour_Request;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.TourRequestMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourRequestService {

    private final TourRequestRepository tourRequestRepository;
    private final PageableHelper pageableHelper;
    private final TourRequestMapper tourRequestMapper;
    private final UserService userService;
    private final AdvertService advertService;



//todo: şu anda eksik advert, owner_user fieldlarının alinmasi gerekiyor.
    public List<TourRequestResponse> getAuthenticatedUserTourRequest(int page, int size, String sort, String type) {

    Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return tourRequestRepository.findAll().stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());

    }
//todo: advert owner_user, guest_user fieldlari eksik.

    public List<TourRequestResponse> getTourRequestForAdmin(int page, int size, String sort, String type) {

    Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return tourRequestRepository.findAll().stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());

    }

    //todo: userservice ile baglantı kurulup user exist kontrol yapilacak ve devami yazilacak.
    public List<TourRequestResponse> getAuthenticatedUserTourRequestDetailById(Long id) {

        //* user id alinicak alinan user id ile repository den tourRequestleri getirilecek.
//*        return tourRequestRepository.findById(id).orElseThrow(()->
//*                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id))
//*                );

        //!!! gecici silinecek.
        return tourRequestRepository.findAll().stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());
    }

    public TourRequestResponse getTourRequestDetailsById(Long id) {
        Tour_Request tour = tourRequestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
        return tourRequestMapper.mapTourRequestToTourRequestResponse(tour);

    }

    //!!!Kocadam bir soru işareti var gibi ???
    public TourRequestResponse createTourRequest(TourRequestRequest tourRequestRequest) {

        Tour_Request request = tourRequestMapper.mapTourRequestRequestToTourRequest(tourRequestRequest);
        Tour_Request allFieldCompletedRequest = request.toBuilder()
                .advert_id(request.getAdvert_id())
                .owner_user_id(request.getOwner_user_id())
                .guest_user_id(request.getGuest_user_id())
                .status(0)
                .build();
        //?Status kısmı int deger yerine String yapıda enum kullanılsa kullanıcıya daha iyi bir response verilebilir
        tourRequestRepository.save(allFieldCompletedRequest);
        return tourRequestMapper.mapTourRequestToTourRequestResponse(allFieldCompletedRequest);
    }

    public ResponseMessage<TourRequestResponse> updateTourRequest(Long id, TourRequestRequest tourRequestRequest) {
        Tour_Request tour = tourRequestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));

        Integer status = tourRequestRequest.getStatus();
        if(status==0|| status ==2){

        }
        else {
            return new BadRequestException ;
        }

    }
}
