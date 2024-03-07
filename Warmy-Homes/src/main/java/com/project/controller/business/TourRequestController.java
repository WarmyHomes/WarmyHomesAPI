package com.project.controller.business;

import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.service.business.TourRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tour-requests")
@RequiredArgsConstructor
public class TourRequestController {

    private final TourRequestService tourRequestService;

    //*S01
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<TourRequestResponse>> getUsersTourRequest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type){
        List<TourRequestResponse> tourRequestResponse = tourRequestService.getUsersTourRequest(page,size,sort,type);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S02
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<List<TourRequestResponse>> getAllTourRequestWithPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ){
        List<TourRequestResponse> tourRequestResponse= tourRequestService.getAllTourRequestWithPage(page,size,sort,type);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S03
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> getUsersTourRequestDetails(@PathVariable Long id){
        TourRequestResponse tourRequestResponse = tourRequestService.getUsersTourRequestDetails(id);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S04
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<TourRequestResponse> getUsersTourRequestDetailsForAdmin(@PathVariable Long id){
        TourRequestResponse tourRequestResponse = tourRequestService.getUsersTourRequestDetailsForAdmin(id);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S05
    @PostMapping("")//!/tour-requests
    @PreAuthorize("hasAnyAuthority('CUSTOMERS')")
    public ResponseEntity<TourRequestResponse> createTourRequest(@RequestBody @Valid TourRequestRequest request){
        TourRequestResponse tourRequestResponse = tourRequestService.createTourRequest(request);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S06
    @PutMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public  ResponseEntity<TourRequestResponse> updateTourRequest(@PathVariable Long id,@RequestBody @Valid TourRequestRequest request){
        return tourRequestService.updateTourRequest(id, request);
    }

    //*S07
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> cancelTourRequest(@PathVariable Long id){
        return tourRequestService.cancelTourRequest(id);
    }

    //*S08
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> approveTourRequest(@PathVariable Long id){
        return tourRequestService.approveTourRequest(id);
    }

    //*S09
    @PatchMapping("/{id}/decline")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> declineTourRequest(@PathVariable Long id){
        return tourRequestService.declineTourRequest(id);
    }

    //*S10
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage deleteTourRequest(@PathVariable Long id){
        return tourRequestService.deleteTourRequest(id);
    }



}
