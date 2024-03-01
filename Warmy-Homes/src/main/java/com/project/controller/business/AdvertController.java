package com.project.controller.business;


import com.project.payload.request.abstracts.AbstractAdvertRequest;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;

@RestController
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @PostMapping("/adverts")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")//A10
    public ResponseEntity<ResponseMessage<AdvertResponse>> saveAdvert (@PathVariable Long id,
                                                                       @RequestBody @Valid BaseAdvertRequest advertRequest){
        return ResponseEntity.ok(advertService.saveAdvert(id,advertRequest));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')") //A01
    public ResponseEntity<Page<AdvertResponse>> allAdvertsByPage (
            @PathVariable String userRole,
            @RequestBody @Valid AbstractAdvertRequest advertRequest,
            @RequestParam(value = "q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type

    ){
        return  null; //new ResponseEntity(advertService.allAdvertsByPage(page,size,sort,type,userRole,advertRequest), HttpStatus.OK);
    }
    //A02
    @GetMapping("/advert/{city}")//normalde task'de cities yazıyor biz city yazdik
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public ResponseEntity<Array<CityResponse>> getCityByAdvert(@RequestParam String request){
        return ResponseEntity.ok(advertService.getCityByAdvert(request));
    }

    @DeleteMapping("/adverts/admin/{advertId}") //A13
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<AdvertResponse> advertDeleteById(@PathVariable Long advertId){
        return advertService.deleteAdvertById(advertId);
    }

}
