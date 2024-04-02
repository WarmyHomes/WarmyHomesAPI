package com.project.payload.mappers;

import com.project.entity.business.Tour_Request;
import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.TourRequestResponse;
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
               .advert_id(request.getAdvert().getId())
               .guest_user(request.getGuest_user().getId())
               .owner_user(request.getOwner_user().getId())
               .update_at(request.getUpdate_at())
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
               .advert_id(request.getAdvert().getId())
               .owner_user(request.getOwner_user().getId())
               .guest_user(request.getGuest_user().getId())
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
