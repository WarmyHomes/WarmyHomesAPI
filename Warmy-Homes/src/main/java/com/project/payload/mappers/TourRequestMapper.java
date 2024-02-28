package com.project.payload.mappers;

import com.project.entity.business.Tour_Request;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.TourRequestResponse;
import org.springframework.stereotype.Component;

@Component
public class TourRequestMapper {

public Tour_Request mapTourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest){
    return Tour_Request.builder()
            .tour_date(tourRequestRequest.getTour_date())
            .tour_time(tourRequestRequest.getTour_time())
            .status(tourRequestRequest.getStatus())
            .create_at(tourRequestRequest.getCreate_at())
            .update_at(tourRequestRequest.getUpdate_at())
            .build();
}
public TourRequestResponse mapTourRequestToTourRequestResponse(Tour_Request tourRequest){
    return TourRequestResponse.builder()
            .id(tourRequest.getId())
            .tour_date(tourRequest.getTour_date())
            .tour_time(tourRequest.getTour_time())
            .status(tourRequest.getStatus())
            .create_at(tourRequest.getCreate_at())
            .update_at(tourRequest.getUpdate_at())
            .build();
}

public Tour_Request mapTourRequestToUpdatedTourRequest(Long id, TourRequestRequest tourRequestRequest){
    return mapTourRequestRequestToTourRequest(tourRequestRequest)
            .toBuilder()
            .id(id)
            .build();
}
}
