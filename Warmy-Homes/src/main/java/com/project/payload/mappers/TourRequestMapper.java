package com.project.payload.mappers;

import com.project.entity.business.Tour_Request;
import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.TourRequestResponse;
import com.project.payload.response.user.UserResponse;
import com.project.service.business.TourRequestService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourRequestMapper {



   public TourRequestResponse mapTourRequestToResponse(Tour_Request request){
       return TourRequestResponse.builder()
               .id(request.getId())
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .status(request.getStatus().getStatusName())
               .update_at(request.getUpdate_at())
               .advert(request.getAdvert())
               .ownerUser(request.getOwner_user())
               .guestUser(request.getGuest_user())
               .build();
   }



    public Tour_Request createTourResponseToTourRequest(TourRequestCreateRequest request){
        return Tour_Request.builder()
                .tour_date(request.getTour_date())
                .tour_time(request.getTour_time())
                .build();
    }

    public TourRequestResponse savedTourRequestToTourRequestResponse(Tour_Request request){
       return TourRequestResponse.builder()
               .id(request.getId())
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .create_at(request.getCreate_at())
               .update_at(request.getUpdate_at())

               .build();
    }

    public List<TourRequestResponse> usersTourRequestToTourRequestResponseList(Page<Tour_Request> req){
       List<Tour_Request> request = req.getContent();
       List<TourRequestResponse> mapped =request.stream().map(this::mapTourRequestToResponse).collect(Collectors.toList());
       return mapped;
    }

    public Tour_Request mapTourRequestUpdateRequestToTourRequest(Tour_Request tr,TourRequestUpdateRequest request){
      return tr.toBuilder()
              .tour_date(request.getTour_date())
              .tour_time(request.getTour_time())
              .update_at(LocalDateTime.now())
              .build();

    }

    public List<TourRequestResponse> mapTourRequestToTourRequestResponseList(List<Tour_Request> req ){
       List<TourRequestResponse> mapped = req.stream().map(this::mapTourRequestToResponse).collect(Collectors.toList());
       return mapped;
    }

}
