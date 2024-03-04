package com.project.controller.business;


import com.project.payload.request.abstracts.AbstractAdvertRequest;
import com.project.payload.request.abstracts.BaseAdvertRequest;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
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
@RequestMapping("/advert")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    // ******************************************** //A10 -- You must check at statements
    @PostMapping("/adverts")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ResponseMessage<AdvertResponse>> saveAdvert (@PathVariable Long id,
                                                                       @RequestBody @Valid BaseAdvertRequest advertRequest){
        return ResponseEntity.ok(advertService.saveAdvert(id,advertRequest));
    }

    // ******************************************** //A01
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')") //A01
    public ResponseEntity<Page<AdvertResponse>> allAdvertsByPage (
            @RequestBody @Valid AbstractAdvertRequest advertRequest,
            @RequestParam(value = "q") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type

    ){
        return  null; //new ResponseEntity(advertService.allAdvertsByPage(page,size,sort,type,userRole,advertRequest), HttpStatus.OK);
    }

    // ******************************************** //A02
    @GetMapping("/advert/cities") //normalde task'de cities yazıyor biz city yazdik
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")

    public ResponseMessage<List<CityForAdvertResponse>> getAdvertsDependingOnCities (@RequestParam String city,
                                                                                        @RequestParam Integer amount){
        return advertService.getAdvertsDependingOnCities(city,amount);
    }

    //****************************************** //A03 yarım kaldı
    @GetMapping("/categories")
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public List<CategoryResponse> getAdvertByCategory(@PathVariable Long id){

        return advertService.getAdvertByCategory(id);
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

    //********************************************//A09 --- BURADA EKSİK VE HATALI YER VAR
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> getAdvertByID(@PathVariable Long id,
                                                         AbstractAdvertRequest advertRequest){

        return advertService.getAdvertById(id,advertRequest);
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
