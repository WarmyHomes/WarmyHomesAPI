package com.project.controller.business;


import com.project.payload.request.business.AdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ResponseMessage<AdvertResponse>> saveAdvert (@PathVariable String userRole,
                                                                       @RequestBody @Valid AdvertRequest advertRequest){
        return ResponseEntity.ok(advertService.saveAdvert(userRole,advertRequest));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public ResponseEntity<Page<AdvertResponse>> allAdvertsByPage (
            @PathVariable String userRole,
            @RequestBody @Valid AdvertRequest advertRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type

    ){
        return  null; //new ResponseEntity(advertService.allAdvertsByPage(page,size,sort,type,userRole,advertRequest), HttpStatus.OK);
    }



}
