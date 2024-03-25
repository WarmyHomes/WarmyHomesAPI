package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Tour_Request;
import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.enums.RoleType;
import com.project.entity.enums.TourStatus;
import com.project.entity.user.User;
import com.project.entity.user.UserRole;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.TourRequestMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourRequestService {

    private final TourRequestRepository tourRequestRepository;
    private final PageableHelper pageableHelper;
    private final TourRequestMapper tourRequestMapper;
    private final UserService userService;



    //*S01
    public ResponseEntity<List<TourRequestResponse>> getUsersTourRequest(int page, int size, String sort, String type, HttpServletRequest servletRequest) {
        String email =(String) servletRequest.getAttribute("email");
        User user =  userService.findUserByEmail(email);
        UserRole roles = user.getUserRole();
        if (!roles.getRoleType().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        Pageable pageable =pageableHelper.getPageableWithProperties(page,size,sort,type);
        Page<Tour_Request> usersTourReq =  tourRequestRepository.findAllByUserId(user.getId(),pageable);

        List<TourRequestResponse> mapped =tourRequestMapper.usersTourRequestToTourRequestResponseList(usersTourReq);
        return ResponseEntity.ok(mapped);
    }

    //*S02
    public  ResponseEntity<List<TourRequestResponse>> getAllTourRequestWithPage(int page, int size, String sort, String type, HttpServletRequest servletRequest) {
        String email = (String) servletRequest.getAttribute("email");
        User user = userService.findUserByEmail(email);
        UserRole role = user.getUserRole();
        if(!role.getRoleType().equals(RoleType.ADMIN)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        Page<Tour_Request> request = tourRequestRepository.findAll(pageable);
        List<TourRequestResponse> mapped = tourRequestMapper.usersTourRequestToTourRequestResponseList(request);
        return ResponseEntity.ok(mapped);
    }

    //*S03
    public TourRequestResponse getUsersTourRequestDetails(Long id, HttpServletRequest servletRequest) {

        UserRole roles = getUsersRole(servletRequest);
        if (!roles.equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        Tour_Request req = isTourRequestExistById(id);
        return tourRequestMapper.mapTourRequestToResponse(req);
    }

    //*S04
    public TourRequestResponse getUsersTourRequestDetailsForAdmin(Long id, HttpServletRequest servletRequest) {
        UserRole roles = getUsersRole(servletRequest);
        if (!roles.equals(RoleType.ADMIN)|| !roles.equals(RoleType.MANAGER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        Tour_Request request = isTourRequestExistById(id);
        return tourRequestMapper.
                mapTourRequestToResponse(request);
    }

    //*S05
    public ResponseEntity<TourRequestResponse> createTourRequest(TourRequestCreateRequest request, HttpServletRequest servletRequest) {
        //*tetikleyen kullanicinin user bilgilerine erismek icin unique degerini aldık ve userrepository e gidip ariyicaz.
        String email =(String) servletRequest.getAttribute("email");
        User guestUser =  userService.findUserByEmail(email);
        if (!guestUser.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        //*ownerUser icin request den algımız advert id ile owner user a ulasıcaz.
        Advert advert = request.getAdvert_id();
        User ownerUser = advert.getUser();
        //* owner ve guest user tourRequest e map islemi.
        Tour_Request createdTourRequest = tourRequestMapper.createTourRequestToTourRequest(request);
        createdTourRequest.toBuilder()
                .owner_user_id(ownerUser)
                .guest_user_id(guestUser)
                .build();
        createdTourRequest.getStatus().setTourStatus(TourStatus.PENDING);
        //* database kayit islemi
        Tour_Request savedTourRequest = tourRequestRepository.save(createdTourRequest);
        return ResponseEntity.ok(tourRequestMapper.savedTourRequestToTourRequestResponse(savedTourRequest));
    }

    //*S06
    public ResponseEntity<TourRequestResponse> updateTourRequest(Long id, TourRequestUpdateRequest request) {
        Tour_Request tourRequest = isTourRequestExistById(id);
        TourStatusRole status = tourRequest.getStatus();
        if (!status.equals(TourStatus.PENDING) || !status.equals(TourStatus.DECLINED)){
            throw new BadRequestException(ErrorMessages.TOUR_REQUEST_CAN_NOT_CHANGED);
        }
        Tour_Request updatedReq = tourRequestMapper.mapTourRequestUpdateRequestToTourRequest(tourRequest,request);
        updatedReq.getStatus().setTourStatus(TourStatus.PENDING);
        Tour_Request savedReq = tourRequestRepository.save(updatedReq);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(savedReq));

    }
    //*S07
    public ResponseEntity<TourRequestResponse> cancelTourRequest(Long id, HttpServletRequest servletRequest) {
        Tour_Request request = isTourRequestExistById(id);
        UserRole roles = getUsersRole(servletRequest);
        if (!roles.equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        request.getStatus().setStatusName(TourStatus.CANCELED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));

    }
    //*S08
    public ResponseEntity<TourRequestResponse> approveTourRequest(Long id, HttpServletRequest servletRequest) {
        Tour_Request request = isTourRequestExistById(id);
        UserRole roles = getUsersRole(servletRequest);
        if (!roles.equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        request.getStatus().setStatusName(TourStatus.APPROVED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));
    }

    public ResponseEntity<TourRequestResponse> declineTourRequest(Long id, HttpServletRequest servletRequest) {
        Tour_Request request = isTourRequestExistById(id);
        UserRole roles = getUsersRole(servletRequest);
        if (!roles.equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        request.getStatus().setStatusName(TourStatus.DECLINED.getStatusName());
        tourRequestRepository.save(request);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(request));

}

    public ResponseMessage  deleteTourRequest(Long id, HttpServletRequest servletRequest) {
        tourRequestRepository.delete(isTourRequestExistById(id));
        return ResponseMessage.builder()
                .message("deleted ")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Tour_Request isTourRequestExistById(Long id ){
        return tourRequestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
    }

    public UserRole getUsersRole(HttpServletRequest servletRequest){
        String email =(String) servletRequest.getAttribute("email");
        User user =  userService.findUserByEmail(email);
        UserRole role = user.getUserRole();
        return role;
    }

    public User getUser(HttpServletRequest servletRequest){
        String email =(String) servletRequest.getAttribute("email");
        User user =  userService.findUserByEmail(email);
        return user;
    }






}
