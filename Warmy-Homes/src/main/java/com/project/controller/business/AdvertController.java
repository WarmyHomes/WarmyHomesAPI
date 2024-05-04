package com.project.controller.business;



import com.project.payload.request.business.AdvertRequestCreate;

import com.project.payload.request.business.AdvertRequestUpdateAdmin;
import com.project.payload.request.business.AdvertRequestUpdateAuth;
import com.project.payload.request.business.helperrequest.AdvertForQueryRequest;
import com.project.payload.response.business.AdvertPageableResponse;
import com.project.payload.response.business.AdvertResponse;

import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.business.helperresponse.AdvertForSlugResponse;
import com.project.payload.response.business.helperresponse.CategoryForAdvertResponse;
import com.project.payload.response.business.helperresponse.CityForAdvertResponse;
import com.project.service.business.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;
        //deneme
    // ******************************************** //A10 Finished
    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ResponseMessage<AdvertResponse>> saveAdvert (@RequestBody @Valid AdvertRequestCreate advertRequest , HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(advertService.saveAdvert(advertRequest, httpServletRequest));
    }


    // ********************************************* //A01 chatgpt
    @GetMapping
    public ResponseEntity<List<AdvertResponse>> allAdvertsQueryByPage(
            @RequestParam(required = false) String q,
            @RequestParam Long category_id,
            @RequestParam Long advert_type_id,
            @RequestParam(required = false) Double price_start,
            @RequestParam(required = false) Double price_end,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "category_id") String sort,
            @RequestParam(defaultValue = "asc") String type
    ) {
        Page<AdvertResponse> adverts = advertService.getAdverts(q, category_id, advert_type_id, price_start, price_end, status, page, size, sort, type);
        return ResponseEntity.ok(adverts.getContent());
    }
//    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ANONYMOUS')") // * A01 Benim yazdığım
//    public Page<AdvertPageableResponse> allAdvertsQueryByPageOld (
//            @RequestBody @Valid AdvertForQueryRequest advertRequest,
//            @RequestParam(value = "q") String q,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "20") int size,
//            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
//            @RequestParam(value = "type", defaultValue = "asc") String type
//
//    ){
//        return  advertService.allAdvertsQueryByPage(advertRequest,q,page,size,sort,type);
//    }



    // ******************************************** //A02 Finished
    @GetMapping("/cities") //normalde task'de cities yazıyor biz city yazdik
//    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
//
    public List<CityForAdvertResponse> getAdvertsDependingOnCities (){

        return advertService.getAdvertsDependingOnCities();
    }

    //****************************************** //A03 Finished
    @GetMapping("/categories")
//    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public List<CategoryForAdvertResponse> getAdvertByCategory(){

        return advertService.getAdvertByCategory();
    }

    // ******************************************** //A05 Finished

    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public Page<AdvertPageableResponse> getAdvertByPageAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "sort", defaultValue = "category_id") String sort,
                                                           @RequestParam(value = "type", defaultValue = "asc") String type,
                                                           HttpServletRequest httpServletRequest
    ){
        return advertService.getAdvertByPageAll(page,size,sort,type, httpServletRequest);
    }

    // *******************************************//A06

    @GetMapping("/admin")

    public ResponseEntity<List<AdvertPageableResponse>> allAdvertsQueryByPageAdmin(
            @RequestParam(required = false) String q,
            @RequestParam Long category_id,
            @RequestParam Long advert_type_id,
            @RequestParam(required = false) Double price_start,
            @RequestParam(required = false) Double price_end,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "category_id") String sort,
            @RequestParam(defaultValue = "asc") String type
    ) {

        Page<AdvertPageableResponse> adverts = advertService.getAdvertsAdminByPage(q, category_id, advert_type_id, price_start, price_end, status, page, size, sort, type);
        return ResponseEntity.ok(adverts.getContent());
    }

    // *******************************************//A07
    @GetMapping("/{slug}")
    public ResponseMessage<AdvertForSlugResponse> getAdvertBySlug(@PathVariable String slug){

        return advertService.getAdvertBySlug(slug);
    }

    //********************************************//A08
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> getCustomerAdvertId(@PathVariable Long id,
                                                               HttpServletRequest httpServletRequest){

        return advertService.getCustomerAdvertId(id,httpServletRequest);
    }


    //********************************************//A09
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> getAdminAdvertById(@PathVariable Long  id , HttpServletRequest httpServletRequest){

        return advertService.getAdminAdvertById(id, httpServletRequest);
    }



    //********************************************//A11
    @PutMapping("/auth/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<AdvertResponse> updateAdvertById (@PathVariable Long id,
                                                             @RequestBody @Valid AdvertRequestUpdateAuth advertRequest,
                                                             HttpServletRequest httpServletRequest){
        return advertService.updateAdvertById(id,advertRequest ,httpServletRequest);
    }


    //********************************************//A12
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> updateAdminAdvertById (@PathVariable Long id,
                                                             @RequestBody @Valid AdvertRequestUpdateAdmin advertRequest,
                                                                  HttpServletRequest httpServletRequest){
        return advertService.updateAdminAdvertById(id,advertRequest, httpServletRequest);
    }

    //********************************************//A13
    @DeleteMapping("/admin/{advertId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertResponse> advertDeleteById(@PathVariable Long advertId, HttpServletRequest httpServletRequest){
        return advertService.deleteAdvertById(advertId, httpServletRequest);
    }



  //  // ******************************************* // A04
    //hocabilgic
    @GetMapping("/popular/{amount}")
    public ResponseEntity<List<AdvertResponse>> getPopularAdverts(@PathVariable int amount) {
        List<AdvertResponse> popularAdverts = advertService.getPopularAdverts(amount);
        return ResponseEntity.ok(popularAdverts);
    }



}
