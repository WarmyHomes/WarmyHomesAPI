package com.project.controller.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.payload.response.business.*;
import com.project.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // G01
    // getAllReport()****
    // http://localhost:8080/report
    @GetMapping("/report")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<GetAllReportResponse> getAllReport(){
        return reportService.getAllReport();
    }

    // G02
    // getAdvertReport()**************
    // http://localhost:8080/report/adverts?date1=2023-02-01&date2=2023-02-28&category=villa&type=rent&status=pending
    @GetMapping("/report/adverts")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<GetAdvertsReportResponse> getAdvertReport(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginningDate,
                                                                     @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endingDate,
                                                                     @RequestParam("category") Category category,
                                                                     @RequestParam("type") Advert_Type advertType){
        return reportService.getAdvertReport(beginningDate, endingDate, category, advertType);
    }


    //G03
    // En popüler ilanları döndüren endpoint
    // http://localhost:8080/report/most-popular-properties?amount=10
    @GetMapping("/report/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public List<AdvertResponse> getMostPopularProperties(@RequestParam(value = "amount", required = true) int amount) {
        return reportService.getMostPopularProperties(amount);
    }


    //G05
    @GetMapping("/report/tour-requests")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public List<TourRequestResponse> getTourRequests(
            @RequestParam("date1") LocalDate startDate,
            @RequestParam("date2") LocalDate endDate,
            @RequestParam("status") String status) {
        return reportService.getTourRequests(startDate, endDate, status);
    }



}
