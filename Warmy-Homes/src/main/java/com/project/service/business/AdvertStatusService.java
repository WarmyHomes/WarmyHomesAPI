package com.project.service.business;

import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.enums.AdvertStatusType;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.repository.business.AdvertStatusRepository;
import com.project.repository.business.TourStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertStatusService {

    private final AdvertStatusRepository advertStatusRepository;

    public AdvertStatusRole getAdvertStatus(AdvertStatusType statusType){
        return advertStatusRepository.findByEnumAdvertStatusEquals(statusType).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ADVERT_STATUS_NOT_FOUND));
    }

    public List<AdvertStatusRole> getAllAdvertStatus(){
        return advertStatusRepository.findAll();
    }

}
