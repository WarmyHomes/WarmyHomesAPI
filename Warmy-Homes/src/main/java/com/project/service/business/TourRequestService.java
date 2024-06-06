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
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.payload.response.user.UserResponse;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TourRequestService {

    private final TourRequestRepository tourRequestRepository;
    private final PageableHelper pageableHelper;
    private final TourRequestMapper tourRequestMapper;
    private final UserService userService;
    private final AdvertService advertService;
    private final TourStatusService tourStatusService;
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

        List<User> ownerUser = usersTourReq.stream().map(Tour_Request::getOwner_user).toList();
        UserResponse guestUser = userToUserRes(user);
        List<Advert> adverts = usersTourReq.stream().map(Tour_Request::getAdvert).toList();

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
       Tour_Request tourRequest = isTourRequestExistById(id);
       String email = (String) servletRequest.getAttribute("email");
       User user = userService.findUserByEmail(email);
       UserRole role = user.getUserRole();
      if(!role.getRoleType().equals(RoleType.CUSTOMER)){
          throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
      }
        return tourRequestMapper.mapTourRequestToResponse(tourRequest);
    }

    //*S04
    public TourRequestResponse getUsersTourRequestDetailsForAdmin(Long id, HttpServletRequest servletRequest) {
        String email = (String) servletRequest.getAttribute("email");
        User user = userService.findUserByEmail(email);
        UserRole role = user.getUserRole();
        //todo: userRole list olmadıgından manager cıkartıp sadece admin icin yazdım. sonradan duzeltilebilir.
        //*role tarafında admin ve manager istiyor fakat list olmadıgından sadece admin icin yaptım
        //permission hatası veriyor.
//        if(!role.equals(RoleType.ADMIN)){
//            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
//        }
        Tour_Request request = isTourRequestExistById(id);
        return tourRequestMapper.
                mapTourRequestToResponse(request);
    }

    //*S05
    public ResponseEntity<TourRequestResponse> createTourRequest(TourRequestCreateRequest request, String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        checkUserRole(RoleType.CUSTOMER,getUserRoleType(userEmail));
        Advert advert = advertService.findAdvertById(request.getAdvert_id());

        Tour_Request createdTourRequest = tourRequestMapper.createTourResponseToTourRequest(request);
        createdTourRequest.setCreate_at(LocalDateTime.now(ZoneId.of("UTC")));
        createdTourRequest.setGuest_user(user);
        createdTourRequest.setOwner_user(advert.getUser());

        createdTourRequest.setStatus(tourStatusService.getTourStatus(TourStatus.PENDING));
        createdTourRequest.setAdvert(advert);
        Tour_Request saved = tourRequestRepository.save(createdTourRequest);
        TourRequestResponse tourRequestResponse = tourRequestMapper.mapTourRequestToResponse(saved);
        return ResponseEntity.ok(tourRequestResponse);

    }

    //*S06
    public ResponseEntity<TourRequestResponse> updateTourRequest(Long id, TourRequestUpdateRequest request, HttpServletRequest servletRequest) {
        validateUserHasRole(servletRequest,RoleType.CUSTOMER);
        Tour_Request tourRequestToUpdate = isTourRequestExistById(id);
        Tour_Request saved = tourRequestMapper.mapTourRequestUpdateRequestToTourRequest(tourRequestToUpdate,request);
        LocalDateTime createTime = saved.getCreate_at();
        saved.setCreate_at(createTime);
        saved.setUpdate_at(LocalDateTime.now());
        tourRequestRepository.save(saved);
        TourRequestResponse response = tourRequestMapper.mapTourRequestToResponse(saved);
        return ResponseEntity.ok(response);

    }

    //*S07-08-09
    public ResponseEntity<TourRequestResponse> updateTourRequestStatus(Long id, HttpServletRequest request, TourStatus newStatus) {

        String email = (String) request.getAttribute("email");
        checkUserRole(RoleType.CUSTOMER,getUserRoleType(email));

        Tour_Request tourRequest = isTourRequestExistById(id);

        TourStatusRole statusRole = tourStatusService.getTourStatus(newStatus);
        tourRequest.setStatus(statusRole);
        tourRequest = tourRequestRepository.save(tourRequest);
        return ResponseEntity.ok(tourRequestMapper.mapTourRequestToResponse(tourRequest));

    }

    //*S10
    public String  deleteTourRequest(Long id, HttpServletRequest servletRequest) {

        UserRole role = getUserRole(getUser(servletRequest));
        if(role.equals(RoleType.ADMIN)|| role.equals(RoleType.MANAGER)){
            tourRequestRepository.delete(isTourRequestExistById(id));
            return SuccessMessages.TOUR_REQUEST_DELETED_SUCCESSFULLY;
        }
        return ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE;
    }

    //*S10.5
    public String deleteTourRequestCustomer(Long id, HttpServletRequest servletRequest) {
        String email =(String) servletRequest.getAttribute("email");
        User user =  userService.findUserByEmail(email);
        UserRole role = user.getUserRole();
        if (!role.getRoleType().equals(RoleType.CUSTOMER)) {
            return ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE;
        }
        tourRequestRepository.delete(isTourRequestExistById(id));
        return SuccessMessages.TOUR_REQUEST_DELETED_SUCCESSFULLY;
    }

    public Tour_Request isTourRequestExistById(Long id ){
        return tourRequestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
    }


    public UserRole getUserRole(User user){
        return user.getUserRole();
    }

    public User getUser(HttpServletRequest servletRequest){
        String email =(String) servletRequest.getAttribute("email");
        User user =  userService.findUserByEmail(email);
        return user;
    }

    private void checkUserRole(RoleType expectedRole, RoleType actualRole){
        if(!actualRole.equals(expectedRole)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    private RoleType getUserRoleType(String email){
        User user = userService.findUserByEmail(email);
        return user.getUserRole().getRoleType();
    }

    private void validateUserHasRole(HttpServletRequest request, RoleType roleType){
        User user = getUser(request);
        if(!user.getUserRole().getRoleType().equals(roleType)){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    private UserResponse userToUserRes(User user){
        return userService.userToUserRes(user);
    }

    private AdvertResponse advertToAdvertRes(Advert advert){
        return advertService.changeAdvertToAdvertResponse(advert);
    }


    //G04
    public List<TourRequestResponse> getTourRequestsFiltered(LocalDate startDate, LocalDate endDate, String status) {
        List<Tour_Request> tourRequests = tourRequestRepository.findByDateRangeAndStatus(startDate, endDate, TourStatus.valueOf(status));
        return tourRequests.stream()
                .map(tourRequestMapper::mapTourRequestToResponse)
                .collect(Collectors.toList());
    }



}
