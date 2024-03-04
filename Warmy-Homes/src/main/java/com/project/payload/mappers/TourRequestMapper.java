package com.project.payload.mappers;

import com.project.entity.business.Tour_Request;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.TourRequestResponse;
import org.springframework.stereotype.Component;

@Component
public class TourRequestMapper {

   public TourRequestResponse mapTourRequestToResponse(Tour_Request request){
       return TourRequestResponse.builder()
               .id(request.getId())
               .tour_date(request.getTour_date())
               .tour_time(request.getTour_time())
               .status(request.getStatus().getStatusType().name())
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
                .status(request.getStatus().getStatusType().name())
                .advert_id(request.getAdvert_id())
                .guest_user(request.getGuest_user_id())
                .build();
    }

    public TourRequestResponse mapTourRequestToResponseWithOutGuest(Tour_Request request){
        return TourRequestResponse.builder()
                .id(request.getId())
                .tour_date(request.getTour_date())
                .tour_time(request.getTour_time())
                .status(request.getStatus().getStatusType().name())
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
               .build();
    }

}
