package com.project.controller.business;


import com.project.payload.request.abstracts.AbstractAdvertRequest;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.request.business.helperrequest.AdvertForQueryRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.helperresponse.CategoryForAdvertResponse;
import com.project.payload.response.business.helperresponse.CityForAdvertResponse;
import com.project.service.business.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    // ******************************************** //A10 -- You must check at statements
    @PostMapping("/adverts")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ResponseMessage<AdvertResponse>> saveAdvert (@PathVariable Long id,
                                                                       @RequestBody @Valid AbstractAdvertRequest advertRequest){
        return ResponseEntity.ok(advertService.saveAdvert(id,advertRequest));
    }

    // ******************************************** //A01
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')") //A01
    public ResponseEntity<Page<AdvertResponse>> allAdvertsQueryByPage (
            @RequestBody @Valid AdvertForQueryRequest advertRequest,
            @RequestParam(value = "q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type

    ){
        return  advertService.allAdvertsQueryByPage(advertRequest,q,page,size,sort,type);
    }

    // ******************************************** //A02
    @GetMapping("/cities") //normalde task'de cities yazıyor biz city yazdik
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")

    public List<CityForAdvertResponse> getAdvertsDependingOnCities (){

        return advertService.getAdvertsDependingOnCities();
    }

    //****************************************** //A03 yarım kaldı
    @GetMapping("/categories")
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public List<CategoryForAdvertResponse> getAdvertByCategory(){

        return advertService.getAdvertByCategory();
    }

    // ******************************************** //A05

    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public Page<AdvertResponse> getAdvertByPageAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                           @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return advertService.getAdvertByPageAll(page,size,sort,type);
    }

    // *******************************************//A07
    @GetMapping("/{slug}")
    public ResponseMessage<AdvertResponse> getAdvertBySlug(@PathVariable String slug){

        return advertService.getAdvertBySlug(slug);
    }

    //********************************************//A08
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> getCustomerAdvertId(@PathVariable Long id){

        return advertService.getCustomerAdvertId(id);
    }


    //********************************************//A09
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> getAdminAdvertById(@PathVariable Long  id){

        return advertService.getAdminAdvertBySlug(id);
    }



    //********************************************//A11
    @PutMapping("/auth/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> updateAdvertById (@PathVariable Long id,
                                                             AbstractAdvertRequest advertRequest){
        return advertService.updateAdvertById(id,advertRequest);
    }


    //********************************************//A12
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> updateAdminAdvertById (@PathVariable Long id,
                                                             AbstractAdvertRequest advertRequest){
        return advertService.updateAdminAdvertById(id,advertRequest);
    }

    //********************************************//A13
    @DeleteMapping("/adverts/admin/{advertId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> advertDeleteById(@PathVariable Long advertId){
        return advertService.deleteAdvertById(advertId);
    }

}
