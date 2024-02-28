package com.project.service.business;

import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.TourRequestMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.response.business.TourRequestResponse;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
}
