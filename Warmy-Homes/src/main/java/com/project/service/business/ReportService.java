package com.project.service.business;


import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.response.business.*;
import com.project.payload.response.user.UserResponse;
import com.project.repository.business.AdvertRepository;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AdvertService advertService;
    private final AdvertTypesService advertTypesService;
    private final CategoryService categoryService;
    private final TourRequestService tourRequestService;
    private final UserService userService;
    private final AdvertRepository advertRepository;


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



    //G03
    // En popüler ilanları döndüren endpoint
    public List<AdvertResponse> getMostPopularProperties(@RequestParam(value = "amount", required = true) int amount) {
        return advertService.getMostPopularProperties(amount);
    }


    public List<TourRequestResponse> getTourRequests(LocalDate startDate, LocalDate endDate, String status) {

        return tourRequestService.getTourRequestsFiltered(startDate, endDate, status);


    }
}

