package com.project.service.business;

import com.project.entity.business.Tour_Request;
import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.enums.StatusType;
import com.project.entity.enums.TourStatus;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.TourRequestMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourRequestService {

    private final TourRequestRepository tourRequestRepository;
    private final PageableHelper pageableHelper;
    private final TourRequestMapper tourRequestMapper;


    //*S01
    public List<TourRequestResponse> getUsersTourRequest(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return tourRequestRepository.findAll(pageable)
                    .stream()
                    .map(tourRequestMapper::mapTourRequestToResponseWithOutGuest)
                    .collect(Collectors.toList());

    }

    //*S02
    public List<TourRequestResponse> getAllTourRequestWithPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return tourRequestRepository.findAll(pageable)
                .stream()
                .map(tourRequestMapper::mapTourRequestToResponse)
                .collect(Collectors.toList());
    }

    //*S03
    public TourRequestResponse getUsersTourRequestDetails(Long id) {
        Tour_Request request = isTourRequestExistById(id);
        return tourRequestMapper.
                mapTourRequestToResponse(request);
    }

    //*S04
    public TourRequestResponse getUsersTourRequestDetailsForAdmin(Long id) {
        Tour_Request request = isTourRequestExistById(id);
        return tourRequestMapper.
                mapTourRequestToResponse(request);
    }

    //*S05
    public TourRequestResponse createTourRequest(TourRequestRequest request) {
        Tour_Request tourRequest = tourRequestMapper.
                mapTourRequestRequestToTour_Request(request);

        Tour_Request savedRequest = tourRequestRepository.
                save(tourRequest);
        return tourRequestMapper.
                mapTourRequestToResponse(savedRequest);
    }

    //*S06
    public ResponseEntity<TourRequestResponse> updateTourRequest(Long id, TourRequestRequest request) {
        if(tourRequestRepository.existsById(id)){
            String status = request.getStatus();
            if(status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("declined")){
                Tour_Request updatedRequest = tourRequestMapper.
                        updateTourRequest(request);
                updatedRequest.getStatus().setStatusName(TourStatus.PENDING.getStatusName());
                Tour_Request savedRequest = tourRequestRepository.
                        save(updatedRequest);
                return ResponseEntity.ok(tourRequestMapper.
                        mapTourRequestToResponse(savedRequest));
            }else {
                return ResponseEntity.
                        badRequest().
                        build();
            }
        }else {
            return ResponseEntity.
                    notFound().
                    build();
        }
    }
    //*S07
    public ResponseEntity<TourRequestResponse> cancelTourRequest(Long id) {
        Tour_Request request = isTourRequestExistById(id);
        //STATUS CANCEL
        request.getStatus().setStatusName(TourStatus.CANCELED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));

    }
    //*S08
    public ResponseEntity<TourRequestResponse> approveTourRequest(Long id) {
        Tour_Request request = isTourRequestExistById(id);
        request.getStatus().setStatusName(TourStatus.APPROVED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));
    }

    public ResponseEntity<TourRequestResponse> declineTourRequest(Long id) {
        Tour_Request request = isTourRequestExistById(id);
        request.getStatus().setStatusName(TourStatus.DECLINED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));

}

    public ResponseMessage  deleteTourRequest(Long id) {
        tourRequestRepository.delete(isTourRequestExistById(id));
        return ResponseMessage.builder()
                .message("deleted ")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Tour_Request isTourRequestExistById(Long id){
        return tourRequestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
    }
}
