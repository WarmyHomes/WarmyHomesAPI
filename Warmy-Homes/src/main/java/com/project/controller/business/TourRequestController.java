package com.project.controller.business;

import com.project.payload.request.business.tourRequestRequests.TourRequestCreateRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestRequest;
import com.project.payload.request.business.tourRequestRequests.TourRequestUpdateRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.service.business.TourRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
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
            @RequestParam(value = "type", defaultValue = "asc") String type,
            HttpServletRequest servletRequest){
        return tourRequestService.getUsersTourRequest(page, size, sort, type, servletRequest);
    }

    //*S02
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<List<TourRequestResponse>> getAllTourRequestWithPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type,
            HttpServletRequest servletRequest
    ){
        List<TourRequestResponse> tourRequestResponse= tourRequestService.getAllTourRequestWithPage(page,size,sort,type, servletRequest);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S03
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> getUsersTourRequestDetails(@PathVariable Long id, HttpServletRequest servletRequest){
        TourRequestResponse tourRequestResponse = tourRequestService.getUsersTourRequestDetails(id,servletRequest);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S04
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<TourRequestResponse> getUsersTourRequestDetailsForAdmin(@PathVariable Long id, HttpServletRequest servletRequest){
        TourRequestResponse tourRequestResponse = tourRequestService.getUsersTourRequestDetailsForAdmin(id,servletRequest);
        return ResponseEntity.ok(tourRequestResponse);
    }

    //*S05
    @PostMapping()//!/tour-requests
    @PreAuthorize("hasAnyAuthority('CUSTOMERS')")
    public ResponseEntity<TourRequestResponse> createTourRequest(@RequestBody @Valid TourRequestCreateRequest request, HttpServletRequest servletRequest){
        return  tourRequestService.createTourRequest(request,servletRequest);

    }

    //*S06
    @PutMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public  ResponseEntity<TourRequestResponse> updateTourRequest(@PathVariable Long id,@RequestBody @Valid TourRequestUpdateRequest request){
        return tourRequestService.updateTourRequest(id, request);
    }

    //*S07
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> cancelTourRequest(@PathVariable Long id,HttpServletRequest servletRequest){
        return tourRequestService.cancelTourRequest(id, servletRequest);
    }

    //*S08
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> approveTourRequest(@PathVariable Long id, HttpServletRequest servletRequest){
        return tourRequestService.approveTourRequest(id, servletRequest);
    }

    //*S09
    @PatchMapping("/{id}/decline")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> declineTourRequest(@PathVariable Long id, HttpServletRequest servletRequest){
        return tourRequestService.declineTourRequest(id, servletRequest);
    }

    //*S10
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage deleteTourRequest(@PathVariable Long id, HttpServletRequest servletRequest){
        return tourRequestService.deleteTourRequest(id, servletRequest);
    }



}
