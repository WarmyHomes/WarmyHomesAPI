package com.project.controller.business;

import com.project.payload.response.business.TourRequestResponse;
import com.project.service.business.TourRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<TourRequestResponse> getAuthenticatedUserTourRequestDetailById(Long id){
        return tourRequestService.getAuthenticatedUserTourRequestDetailById(id);
    }

}
