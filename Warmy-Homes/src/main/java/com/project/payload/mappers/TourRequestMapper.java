package com.project.payload.mappers;

import com.project.entity.business.Tour_Request;
import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.TourRequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
               .advert_id(request.getAdvert_id())
               .guest_user(request.getGuest_user_id())
               .owner_user(request.getOwner_user_id())
               .build();
   }

    public TourRequestResponse mapTourRequestToResponseWithOutOwner(Tour_Request request){
        return TourRequestResponse.builder()
                .id(request.getId())
                .tour_date(request.getTour_date())
                .tour_time(request.getTour_time())
                //.status(request.getStatus().getStatusType().name())
                .advert_id(request.getAdvert_id())
                .guest_user(request.getGuest_user_id())
                .build();
    }

    public TourRequestResponse mapTourRequestToResponseWithOutGuest(Tour_Request request){
        return TourRequestResponse.builder()
                .id(request.getId())
                .tour_date(request.getTour_date())
                .tour_time(request.getTour_time())
                //.status(request.getStatus().getStatusType().name())
                .advert_id(request.getAdvert_id())
                .owner_user(request.getOwner_user_id())
                .build();
    }

    public Tour_Request mapTourRequestRequestToTour_Request(TourRequestRequest request){
       return Tour_Request.builder()
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .advert_id(request.getAdvert_id())
               .build();
    }

    public Tour_Request updateTourRequest(TourRequestRequest request){
       return Tour_Request.builder()
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .advert_id(request.getAdvert_id())
               //.status()
               .build();
    }

    public Tour_Request createTourRequestToTourRequest(TourRequestCreateRequest request){
        return Tour_Request.builder()
                .tour_date(request.getTour_date())
                .tour_time(request.getTour_time())
                .advert_id(request.getAdvert_id())
                .build();
    }

    public TourRequestResponse savedTourRequestToTourRequestResponse(Tour_Request request){
       return TourRequestResponse.builder()
               .id(request.getId())
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .status(request.getStatus().getStatusName())
               .create_at(request.getCreate_at())
               .update_at(request.getUpdate_at())
               .advert_id(request.getAdvert_id())
               .owner_user(request.getOwner_user_id())
               .guest_user(request.getGuest_user_id())
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
              .advert_id(request.getAdvert_id())
              .build();

    }

}
