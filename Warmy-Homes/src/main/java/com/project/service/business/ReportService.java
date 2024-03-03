package com.project.service.business;


import com.project.entity.business.Advert;
import com.project.payload.request.business.GetAdvertsReportRequest;
import com.project.payload.response.business.AddressResponse;
import com.project.payload.response.business.GetAdvertsReportResponse;
import com.project.payload.response.business.GetAllReportResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.AdvertTypesRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.business.TourRequestRepository;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AdvertRepository advertRepository;
    private final AdvertTypesRepository advertTypeRepository;
    private final CategoryRepository categoryRepository;
    private final TourRequestRepository tourRequestRepository;
    private final UserRepository userRepository;


    // G01
    // getAllReport()****
    // http://localhost:8080/report
    public ResponseMessage<GetAllReportResponse> getAllReport() {

        Long publishedCategories = categoryRepository.count();
        Long publishedAdverts = advertRepository.count();
        Long advertTypeCount = advertTypeRepository.count();
        Long tourRequestCount = tourRequestRepository.count();
        Long customerCount = userRepository.count();

        GetAllReportResponse reportResponse = new GetAllReportResponse(
                publishedCategories,
                publishedAdverts,
                advertTypeCount,
                tourRequestCount,
                customerCount
        );

        return ResponseMessage.<GetAllReportResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(reportResponse)
                .build();
    }

    // G02
    public ResponseMessage<GetAdvertsReportResponse> getAdvertReport(GetAdvertsReportRequest request) {

        List<Advert> adverts = advertRepository
                .findAdvertsByFilter(request.getBeginningDate(),
                                     request.getEndingDate(),
                                     request.getCategory(),
                                     request.getAdvertType());

        GetAdvertsReportResponse reportResponse = new GetAdvertsReportResponse(adverts);

        return ResponseMessage.<GetAdvertsReportResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(reportResponse)
                .build();
    }
}
