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
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AdvertService advertService;
    private final AdvertTypesService advertTypesService;
    private final CategoryService categoryService;
    private final TourRequestService tourRequestService;
    private final UserService userService;


    // G01
    // getAllReport()****
    // http://localhost:8080/report
    public ResponseMessage<GetAllReportResponse> getAllReport() {

        Long publishedCategories = categoryService.countAllCategories();
        Long publishedAdverts = advertService.countAllAdvert();
        Long advertTypeCount = advertTypesService.countAllAdvertType();
        // Long tourRequestCount = tourRequestService.countAllTour();
        Long customerCount = userService.countAllUser();

        GetAllReportResponse reportResponse = new GetAllReportResponse(
                publishedCategories,
                publishedAdverts,
                advertTypeCount,
                customerCount
        );

        return ResponseMessage.<GetAllReportResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(reportResponse)
                .build();
    }

    // G02
    public ResponseMessage<GetAdvertsReportResponse> getAdvertReport(GetAdvertsReportRequest request) {

        List<Advert> adverts = advertService
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
