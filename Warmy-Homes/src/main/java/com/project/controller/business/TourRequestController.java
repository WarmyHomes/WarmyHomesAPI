package com.project.controller.business;

import com.project.payload.request.business.TourRequestRequest;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.TourRequestResponse;
import com.project.service.business.TourRequestService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public List<TourRequestResponse> getAuthenticatedUserTourRequest(
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "20")int size,
            @RequestParam(value = "sort",defaultValue = "category_id")String sort,
            @RequestParam(value = "type",defaultValue = "asc")String type
    ){
        return tourRequestService.getAuthenticatedUserTourRequest(page,size,sort,type);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','ANONYMOUS')")
    public List<TourRequestResponse>getTourRequestForAdmin(
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "20")int size,
            @RequestParam(value = "sort",defaultValue = "category_id")String sort,
            @RequestParam(value = "type",defaultValue = "asc")String type
    ){
        return tourRequestService.getTourRequestForAdmin(page,size,sort,type);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public List<TourRequestResponse> getAuthenticatedUserTourRequestDetailById(@PathVariable Long id){
        return tourRequestService.getAuthenticatedUserTourRequestDetailById(id);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public TourRequestResponse getTourRequestDetailsById(@PathVariable Long id){
        return tourRequestService.getTourRequestDetailsById(id);
    }

    @PostMapping("/tour-requests")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public TourRequestResponse createTourRequest(@RequestBody @Valid TourRequestRequest tourRequestRequest){
        return tourRequestService.createTourRequest(tourRequestRequest);
    }

    @PutMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> updateTourRequest(@PathVariable Long id, @RequestBody @Valid TourRequestRequest tourRequestRequest){
        return tourRequestService.updateTourRequest(id, tourRequestRequest);
    }
}
