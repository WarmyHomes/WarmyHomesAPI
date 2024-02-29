package com.project.service.business;

import com.project.payload.request.business.AdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertService {


    public ResponseMessage<AdvertResponse> saveAdvert(String userRole, AdvertRequest advertRequest) {

    }

    public Page<AdvertResponse> allAdvertsByPage(int page, int size, String sort, String type, String userRole, AdvertRequest advertRequest) {

        return null;
    }
}
