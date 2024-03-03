package com.project.service.business;

import com.project.entity.business.Tour_Request;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.TourRequestMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.TourRequestResponse;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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


//*S01
    public ResponseEntity<List<TourRequestResponse>> getAuthenticatedUserTourRequest(int page, int size, String sort, String type) {

    Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

    List<TourRequestResponse> req = tourRequestRepository.findAll(pageable).stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());

    return ResponseEntity.ok(req);
    }
//todo: advert owner_user, guest_user fieldlari eksik.
//*S02
    public ResponseEntity<List<TourRequestResponse>> getTourRequestForAdmin(int page, int size, String sort, String type) {

    Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        List<TourRequestResponse> req =   tourRequestRepository.findAll(pageable).stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());

        return ResponseEntity.ok(req);
    }
//*S03
    public ResponseEntity<TourRequestResponse> getAuthenticatedUserTourRequestDetailById(Long id) {
        Tour_Request request = tourRequestRepository.findById(id).orElseThrow(() -> new BadRequestException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, id)));
        TourRequestResponse mappedRequest = tourRequestMapper.mapTourRequestToTourRequestResponse(request);
        return ResponseEntity.ok(mappedRequest);
    }

    //*S04
    public ResponseEntity<TourRequestResponse> getTourRequestDetailsById(Long id) {
        Tour_Request tour = tourRequestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
        TourRequestResponse mappedRequest =tourRequestMapper.mapTourRequestToTourRequestResponse(tour);
        return ResponseEntity.ok(mappedRequest);
    }

    //*S05
    public  ResponseEntity<TourRequestResponse> createTourRequest(TourRequestRequest tourRequestRequest) {
        Tour_Request request = tourRequestMapper.createTourRequestToTourRequest(tourRequestRequest);
        Tour_Request savedRequest =tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToTourRequestResponse(savedRequest));
    }


    //*S06
    public ResponseEntity<TourRequestResponse> updateTourRequest(Long id, TourRequestRequest tourRequestRequest) {
        Integer status = tourRequestRequest.getStatus();

        if (tourRequestRepository.existsById(id)){
            if (status == 0 || status == 3){
                return ResponseEntity.badRequest().build();
            }else{
                Tour_Request tourRequest =tourRequestMapper.mapTourRequestRequestToUpdatedTourRequest(tourRequestRequest,id);
                tourRequest.setStatus(0);
                tourRequestRepository.save(tourRequest);
                return ResponseEntity.ok(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequest));
            }

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    //*S07
    public ResponseEntity<TourRequestResponse> cancelTourRequest(Long id) {

        if(tourRequestRepository.existsById(id)){
            Tour_Request tourRequest = tourRequestRepository.findById(id).orElseThrow(() -> new BadRequestException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, id)));
            tourRequest.setStatus(2);
            return ResponseEntity.ok(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequest));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }



}
