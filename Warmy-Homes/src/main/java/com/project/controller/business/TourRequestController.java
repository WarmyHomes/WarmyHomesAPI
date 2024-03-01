package com.project.controller.business;

import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.request.business.TourRequestRequest;
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

//@PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER','ANONYMOUS')")
//*S01
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<TourRequestResponse>> getAuthenticatedUserTourRequest(
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "20")int size,
            @RequestParam(value = "sort",defaultValue = "category_id")String sort,
            @RequestParam(value = "type",defaultValue = "asc")String type
    ){
        return tourRequestService.getAuthenticatedUserTourRequest(page,size,sort,type);
    }
    //*S02
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','ANONYMOUS')")
    public ResponseEntity<List<TourRequestResponse>>getTourRequestForAdmin(
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "20")int size,
            @RequestParam(value = "sort",defaultValue = "category_id")String sort,
            @RequestParam(value = "type",defaultValue = "asc")String type
    ){
        return tourRequestService.getTourRequestForAdmin(page,size,sort,type);
    }

    //*S03
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> getAuthenticatedUserTourRequestDetailById(@PathVariable Long id){
        return tourRequestService.getAuthenticatedUserTourRequestDetailById(id);
    }
    //*S04
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<TourRequestResponse> getTourRequestDetailsById(@PathVariable Long id){
        return tourRequestService.getTourRequestDetailsById(id);
    }
    //*S05
    @PostMapping("/tour-requests")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public  ResponseEntity<TourRequestResponse> createTourRequest(@RequestBody @Valid TourRequestRequest tourRequestRequest){
        return tourRequestService.createTourRequest(tourRequestRequest);
    }
    //*S06
    @PutMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> updateTourRequest(@PathVariable Long id, @RequestBody @Valid TourRequestRequest tourRequestRequest){
        return tourRequestService.updateTourRequest(id, tourRequestRequest);
    }

    //*S07
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<TourRequestResponse> cancelTourRequest(@PathVariable Long id){
        return tourRequestService.cancelTourRequest(id);
    }
}
