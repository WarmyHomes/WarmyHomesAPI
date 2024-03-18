package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.enums.AdvertStatusType;
import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import com.project.exception.BadRequestException;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.AdvertMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
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
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.business.CityRepository;
import com.project.repository.business.TourRequestRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final PageableHelper pageableHelper;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    // ******************************************** // A10
    public ResponseMessage<AdvertResponse> saveAdvert( AdvertRequestCreate advertRequest ) {

            Advert advertMap = advertMapper.mapSaveAdvertRequestToAdvert(advertRequest);
            Advert savedAdvert = advertRepository.save(advertMap);


        AdvertResponse advertResponse = advertMapper.mapSaveAdvertToAdvertResponse(savedAdvert);


        return ResponseMessage.<AdvertResponse>builder()
                .object(advertResponse)
                .message(SuccessMessages.ADVERT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    // ***************************************** A01
    public Page<AdvertResponse> getAdverts(String q, Long category_id, Long advert_type_id,
                                           Double price_start, Double price_end, Integer status, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        if (q != null) {
            return advertMapper.mapAdvertToAdvertResponse( advertRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q, pageable));
        } else {

            return advertMapper.mapAdvertToAdvertResponse( advertRepository.findAllByCategoryIdAndAdvertTypeIdAndPriceBetweenAndStatusOrderBy(pageable, category_id, advert_type_id, price_start, price_end, status, sort, type));
        }
    }




//    // ******************************************** // A01
//    public Page<AdvertPageableResponse> allAdvertsQueryByPage(AdvertForQueryRequest advertRequest, String q, int page, int size, String sort, String type) {
//        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
//        if (q != null){
//            Advert advertQuery = advertMapper.mapAdvertQueryToAdvert(advertRequest);
//
////            Page<Advert> advertPage = advertRepository.findByTitleOrDescriptionEquals(advertQuery.getTitle(),pageable);
////            advertMapper.mapQueryPageAdvertToAdvertResponse(advertPage);
//            return null;
//
//        }
//            return null;
//    }


    // ******************************************** //A02
    public List<CityForAdvertResponse> getAdvertsDependingOnCities() {

            List<Object[]> cities = cityRepository.countCities();


           return cities.stream().map(objects -> CityForAdvertResponse.builder()
                   .city((String) objects[0])
                   .amount((Integer) objects[1])
                   .build()).collect(Collectors.toList());

            };



    // ****************HELPER METHODE*************
    private  Advert isAdvertExist(Long id){
        return advertRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND)));
    }

    // ******************************************** //A03

    public List<CategoryForAdvertResponse> getAdvertByCategory() {
         List<Object[]> categories = categoryRepository.countCategories();

        return categories.stream().map(objects -> CategoryForAdvertResponse.builder()
                .category((String) objects[0])
                .amount((Integer) objects[1])
                .build()).collect(Collectors.toList());
    }

    // ******************************************** //A05
    public Page<AdvertPageableResponse> getAdvertByPageAll(int page, int size, String sort, String type, HttpServletRequest httpServletRequest) {

        User authorized = (User) httpServletRequest.getAttribute("email");
        if (!authorized.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return advertRepository.findAll(pageable).map(advertMapper::mapPageAdvertToAdvertResponse);
    }

    // *******************************************//A06
    public Page<AdvertPageableResponse> getAdvertsAdminByPage(String q, Long category_id, Long advert_type_id,
                                                              Double price_start, Double price_end, Integer status, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return null;
    }



    // *******************************************//A07
    public ResponseMessage<AdvertForSlugResponse> getAdvertBySlug(String slug) {

        // ! Bu isim de bir slug var mi ?
        isAdvertExistByAdvertSlug(slug);

        Advert advert = advertRepository.findBySlugContaining(slug);
        AdvertForSlugResponse advertResponse = advertMapper.mapAdvertGetSlugToAdvertResponse(advert);

        return ResponseMessage.<AdvertForSlugResponse>builder()
                .object(advertResponse)
                .message(SuccessMessages.GET_SLUG)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    // ***************** HELPER METHODE ************************
    private boolean isAdvertExistByAdvertSlug(String slug){

        boolean advertExist = advertRepository.existsAdvertBySlug(slug);
        if (advertExist){
            throw  new ConflictException(ErrorMessages.ADVERT_ALREADY_EXIST);
        }else {
            return false;
        }
    }

    // ****************************************** / A08
    public ResponseMessage<AdvertResponse> getCustomerAdvertId(Long id,HttpServletRequest httpServletRequest) {
        User authorized = (User) httpServletRequest.getAttribute("email");
        if (!authorized.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }
        Advert advert = isAdvertExist(id);

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(advert))
                .message(SuccessMessages.GET_ADVERT)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    //******************************************** //A09
    public ResponseMessage<AdvertResponse> getAdminAdvertById(Long id) {
            Advert advert = isAdvertExist(id);


        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(advert))
                .message(SuccessMessages.GET_ADVERT)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    // ****************************************** / A11
    public ResponseMessage<AdvertResponse> updateAdvertById(Long id, AdvertRequestUpdateAuth advertRequest) {
        // ! Boyle bir advert var mı ?
        Advert advertCustomer = isAdvertExist(id);

        // ! Role type kontrolu

        if (!advertCustomer.getUser().getUserRole().equals(RoleType.CUSTOMER)){

        if (advertCustomer.getUser().getUserRole().getRoleType().equals(RoleType.CUSTOMER)){

            throw new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND));
        }
        }
        // ! Advert Built-in mi ?
        if (advertCustomer.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }

        Advert advertMap = advertMapper.mapAdvertUpdateRequestToAdvert(advertRequest);

        // * PENDING islemi yapilacak
        advertMap.getStatus().setAdvertStatusId(AdvertStatusType.PENDING.getId()); // * Çalışması kontrol edilmesi gerek
        Advert updateAdvert = advertRepository.save(advertMap);



        return ResponseMessage.<AdvertResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(advertMapper.mapAdvertToAdvertResponse(updateAdvert))
                .message(SuccessMessages.ADVERT_UPDATED)
                .build();
    }

    // ****************************************** / A12
    public ResponseMessage<AdvertResponse> updateAdminAdvertById (Long id, AdvertRequestUpdateAdmin advertRequest) {
        Advert advert = isAdvertExist(id);

        // ! Advert Built-in mi ?
        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }
        Advert advertMap = advertMapper.mapAdvertUpdateAdminRequestToAdvert(advertRequest);
        Advert updateAdvert = advertRepository.save(advertMap);

        return ResponseMessage.<AdvertResponse>builder()
                .message(SuccessMessages.ADVERT_UPDATED)
                .object(advertMapper.mapAdvertToAdvertResponse(updateAdvert))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // ******************************************** //A13
    public ResponseMessage<AdvertResponse> deleteAdvertById (Long advertId) {

        Advert advert = isAdvertExist(advertId);
        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }
        AdvertResponse advertResponse = advertMapper.mapAdvertToAdvertResponse(advert);
        advertRepository.deleteById(advertId);


        return ResponseMessage.<AdvertResponse>builder()
                .object(advertResponse)
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.ADVERT_DELETED)
                .build();
    }

    //bilgichoca
    public boolean isAdvertTypeUsed(Long advertTypeId) {

        List<Advert> adverts = advertRepository.findAll();

        for(Advert advert:adverts){
            if (advert.getAdvert_type_id().getId()==advertTypeId){
                throw new IllegalStateException(ErrorMessages.ADVERT_TYPE_IN_USE_ERROR_MESSAGE);
            }
        }

        return false;
    }

    private final TourRequestRepository tourRequestRepository;

    // // *************************************** // A04
    public List<AdvertResponse> getPopularAdverts(int amount) {
        // Popüler reklamları almak için gerekli hesaplama yapılır
        List<Advert> popularAdverts = advertRepository.findAll();
        if (popularAdverts == null || popularAdverts.isEmpty() || amount <= 0) {
            throw new IllegalArgumentException("There are no popular adverts to retrieve.");
        }


        popularAdverts.sort(Comparator.comparingInt(this::calculatePopularity).reversed());

        int endIndex = Math.min(amount, popularAdverts.size());
        List<Advert> selectedAdverts = popularAdverts.subList(0, endIndex);

        return advertMapper.mapAdvertToAdvertResponse(selectedAdverts);
    }

    private int calculatePopularity(Advert advert) {
        int totalTourRequests = tourRequestRepository.countByAdvert(advert);
        int totalViews = advert.getViewCount();
        // Popülerlik puanı hesaplaması
        return 3 * totalTourRequests + totalViews;
    }



    // NOT: This method wrote for Report.
    public Long countAllAdvert() {
       return advertRepository.countAllAdvert();
    }

    // NOT: This method wrote for Report.
   // public List<Advert> findAdvertsByFilter(LocalDate beginningDate,
   //                                         LocalDate endingDate,
   //                                         Category category, Advert_Type advertType) {
//
   //    return advertRepository.findAdvertsByFilter(beginningDate, endingDate,category, advertType);
   // }


}


