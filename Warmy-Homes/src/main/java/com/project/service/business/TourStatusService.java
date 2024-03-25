package com.project.service.business;

import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.enums.TourStatus;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.business.TourStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourStatusService {

    private final TourStatusRepository tourStatusRepository;

    public TourStatusRole getTourStatus(TourStatus status){
        return tourStatusRepository.findByEnumTourStatusEquals(status).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.TOUR_STATUS_NOT_FOUND));
    }

    public List<TourStatusRole> getAllStatus(){
        return tourStatusRepository.findAll();
    }


}
