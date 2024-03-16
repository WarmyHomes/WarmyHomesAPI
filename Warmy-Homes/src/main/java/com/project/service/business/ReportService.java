package com.project.service.business;


import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.response.business.GetAdvertsReportResponse;
import com.project.payload.response.business.GetAllReportResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    // getAdvertReport()**************
    // http://localhost:8080/report/adverts?date1=2023-02-01&date2=2023&category=villa&type=rent&status=pending
    public ResponseMessage<GetAdvertsReportResponse> getAdvertReport(LocalDate beginningDate,
                                                                     LocalDate endingDate,
                                                                     Category category,
                                                                     Advert_Type advertType) {
        List<Advert> adverts = advertService.findAdvertsByFilter(beginningDate, endingDate,category, advertType);

        GetAdvertsReportResponse reportResponse = new GetAdvertsReportResponse(adverts);

        return ResponseMessage.<GetAdvertsReportResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(reportResponse)
                .build();
    }


}
