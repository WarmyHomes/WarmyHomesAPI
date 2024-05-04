package com.project.service.business;

import com.project.entity.business.*;
import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.entity.business.helperentity.Category_Property_Value;
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
import com.project.repository.business.*;
import com.project.repository.helperRepository.CategoryPropertyValueRepository;
import com.project.repository.user.UserRepository;
import com.project.service.helper.AdvertHelper;
import com.project.service.helper.CategoryHelper;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final PageableHelper pageableHelper;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    private final CategoryHelper categoryHelper;
    private final AdvertHelper advertHelper;
    private final UserRepository userRepository;
   // private final ImageService imageService;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

    // ******************************************** // A10
    public ResponseMessage<AdvertResponse> saveAdvert( AdvertRequestCreate advertRequest, HttpServletRequest httpServletRequest ) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }

        Category category = advertHelper.isCategoryExist(advertRequest.getCategory_id());
        Advert_Type advertType = advertHelper.isAdvert_TypeExist(advertRequest.getAdvert_type_id());
        Country country = advertHelper.isCountryExist(advertRequest.getCountry_id());
        City city = advertHelper.isCityExist(advertRequest.getCity_id());
        District district = advertHelper.isDistrictExist(advertRequest.getDistrict());

       // List<Long> imagesId = advertRequest.getImages();
//        for (Long imageId : imagesId){
//            imageService.getImageById(imageId);
//        }

        //List<Image> image = imageRepository.findAllById(advertRequest.getImages());


            Advert advertMap = advertMapper.mapSaveAdvertRequestToAdvert(advertRequest);
            advertMap.setCreatedAt(LocalDateTime.now());
            advertMap.setAdvert_type(advertType);
            advertMap.setCategory(category);
            advertMap.setCountry(country);
            advertMap.setCity(city);
            advertMap.setDistrict(district);
            advertMap.setIsActive(false);
            advertMap.setBuiltIn(false);
            advertMap.setUser(user);
            advertMap.setStatus((byte)0);
            //advertMap.getStatus().setAdvertStatusId(AdvertStatusType.PENDING.getId()); // * AdvertStatusType ı iptal edip status u byte olarak tanımladım
            // * advertMap.setStatus(AdvertStatusType.PENDING.id); // * Seni bulacam oğlum
            //advertMap.setImages(image);


        List<Category_Property_Value> category_property_values=advertRequest.getCategory_property_values();
        List<Category_Property_Key> categoryPropertyKeys=category.getCategory_property_keys();

        for (int i = 0; i <categoryPropertyKeys.size() ; i++) {
            category_property_values.get(i).setCategory_property_key(categoryPropertyKeys.get(i));
            category_property_values.get(i).setId(advertMap.getId());
        }


        for (Category_Property_Value k:category_property_values) {
            categoryPropertyValueRepository.save(k);


        }

        advertMap.setCategory_property_values(category_property_values);

            Advert advertsavedd = advertRepository.save(advertMap);

        for (Category_Property_Value cpv : category_property_values) {
            cpv.setAdvert(advertsavedd);
        }

        for (Category_Property_Value k:category_property_values) {
            categoryPropertyValueRepository.save(k);


        }


            // * slug islemleri kontrol edilmeli, duzgun calisiyor mu diye
            String slug = categoryHelper.toSlug(advertMap.getTitle(),advertMap.getId());
            boolean isExistSlug = advertRepository.existsAdvertBySlug(slug);
            if (isExistSlug){
                throw new BadRequestException(ErrorMessages.SLUG_IS_ALREADY_EXISTS);
            }
            advertMap.setSlug(slug);
            //advertMap.setBuiltIn(false); gerek yok gibi ama kontrol etmem lazım. Advert entity de builtin i false olarak belirlenmiş zaten aksi belirtilmedikçe

            Advert savedAdvertSlug = advertRepository.save(advertMap);


        AdvertResponse advertResponse = advertMapper.mapSaveAdvertToAdvertResponse(savedAdvertSlug);
//        advertResponse.setAdvert_type_id(advertType);
//        advertResponse.setDistrict(district);
//        advertResponse.setCity_id(city);
//        advertResponse.setCountry_id(country);
//        advertResponse.setCategory_property_values(category_property_values);


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
//        if (q != null) {
//            return advertMapper.mapAdvertToAdvertResponse( advertRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q, pageable));
//        } else {

            return advertMapper.mapAdvertToAdvertResponse( advertRepository.searchAllProducts(q, category_id, advert_type_id, price_start, price_end,pageable));
        //}
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

        // ! Role type kontrolu
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.CUSTOMER)){
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
        if (!advertExist){
            throw  new ConflictException(ErrorMessages.ADVERT_ALREADY_EXIST);
        }else {
            return false;
        }
    }

    // ****************************************** / A08
    public ResponseMessage<AdvertResponse> getCustomerAdvertId(Long id,HttpServletRequest httpServletRequest) {
        // ! Role type kontrolu
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }

        Advert advert = advertHelper.isAdvertExist(id);

        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(advert))
                .message(SuccessMessages.GET_ADVERT)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    //******************************************** //A09
    public ResponseMessage<AdvertResponse> getAdminAdvertById(Long id, HttpServletRequest httpServletRequest) {
        // ! Role type kontrolu
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.ADMIN)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }

        Advert advert = advertHelper.isAdvertExist(id);


        return ResponseMessage.<AdvertResponse>builder()
                .object(advertMapper.mapAdvertToAdvertResponse(advert))
                .message(SuccessMessages.GET_ADVERT)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    // ****************************************** / A11
    public ResponseMessage<AdvertResponse> updateAdvertById(Long id, AdvertRequestUpdateAuth advertRequest ,HttpServletRequest httpServletRequest) {

        // ! Role type kontrolu
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.CUSTOMER)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }

        Category category = advertHelper.isCategoryExist(advertRequest.getCategory_id());
        Advert_Type advertType = advertHelper.isAdvert_TypeExist(advertRequest.getAdvert_type_id());
        Country country = advertHelper.isCountryExist(advertRequest.getCountry_id());
        City city = advertHelper.isCityExist(advertRequest.getCity_id());
        District district = advertHelper.isDistrictExist(advertRequest.getDistrict());

        // ! Boyle bir advert var mı ?
        Advert advertCustomer = advertHelper.isAdvertExist(id);
        LocalDateTime createAt =advertCustomer.getCreatedAt();


        // ! Advert Built-in mi ?
        if (advertCustomer.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }
        // ! Image kontrol
//        List<Long> imageList = advertRequest.getImages();
//        List<Image> currentImages = imageService.findWithAdvert(id);


        Advert advertMap = advertMapper.mapAdvertUpdateRequestToAdvert(advertRequest);
        advertMap.setCreatedAt(createAt);
        advertMap.setUpdated_at(LocalDateTime.now());
        advertMap.setAdvert_type(advertType);
        advertMap.setCategory(category);
        advertMap.setCountry(country);
        advertMap.setCity(city);
        advertMap.setDistrict(district);
        advertMap.setIsActive(false);
        advertMap.setBuiltIn(false);
        advertMap.setUser(user);
        advertMap.setStatus((byte) 0);

        // * Slug islemi calisiyor mu diye kontrol edilmeli
        String slug = categoryHelper.toSlug(advertMap.getTitle(),advertMap.getId());
        boolean isExistSlug = advertRepository.existsAdvertBySlug(slug);
//        if (isExistSlug){
//            throw new BadRequestException(ErrorMessages.SLUG_IS_ALREADY_EXISTS);
//        }
        advertMap.setSlug(slug);
        
        Advert updateAdvert = advertRepository.save(advertMap);


        return ResponseMessage.<AdvertResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(advertMapper.mapAdvertToAdvertResponse(updateAdvert))
                .message(SuccessMessages.ADVERT_UPDATED)
                .build();
    }

    // ****************************************** / A12
    public ResponseMessage<AdvertResponse> updateAdminAdvertById (Long id, AdvertRequestUpdateAdmin advertRequest , HttpServletRequest httpServletRequest) {
        // ! Role type kontrolu
        String email = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmail(email);
        if (user.getUserRole().equals(RoleType.ADMIN)){
            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
        }

        Advert advert = advertHelper.isAdvertExist(id);
        LocalDateTime createTime = advert.getCreatedAt();
        Category category = advertHelper.isCategoryExist(advertRequest.getCategory_id());
        Advert_Type advertType = advertHelper.isAdvert_TypeExist(advertRequest.getAdvert_type_id());
        Country country = advertHelper.isCountryExist(advertRequest.getCountry_id());
        City city = advertHelper.isCityExist(advertRequest.getCity_id());
        District district = advertHelper.isDistrictExist(advertRequest.getDistrict());

        // ! Advert Built-in mi ?
        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }
        Advert advertMap = advertMapper.mapAdvertUpdateAdminRequestToAdvert(advertRequest);
        advertMap.setCreatedAt(createTime);
        advertMap.setUpdated_at(LocalDateTime.now());
        advertMap.setAdvert_type(advertType);
        advertMap.setCategory(category);
        advertMap.setCountry(country);
        advertMap.setCity(city);
        advertMap.setDistrict(district);
        advertMap.setIsActive(false);
        advertMap.setBuiltIn(false);
        advertMap.setUser(user);

        Advert updateAdvert = advertRepository.save(advertMap);

        return ResponseMessage.<AdvertResponse>builder()
                .message(SuccessMessages.ADVERT_UPDATED)
                .object(advertMapper.mapSaveAdvertToAdvertResponse(updateAdvert))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // ******************************************** //A13
    public ResponseMessage<AdvertResponse> deleteAdvertById (Long advertId, HttpServletRequest httpServletRequest) {
//        // ! Role type kontrolu
//        String email = (String) httpServletRequest.getAttribute("email");
//        User user = userRepository.findByEmail(email);
//        if (!user.getUserRole().equals(RoleType.ADMIN)){
//            throw new BadRequestException(ErrorMessages.NOT_FOUND_USER_USERROLE_MESSAGE);
//        }

        Advert advert = advertHelper.isAdvertExist(advertId);

        if (advert.getBuiltIn().equals(Boolean.TRUE)){
            throw new ConflictException(ErrorMessages.ADVERT_BUILD_IN);
        }
        AdvertResponse advertResponse = advertMapper.mapSaveAdvertToAdvertResponse(advert);
        advertRepository.deleteById(advertId);


        return ResponseMessage.<AdvertResponse>builder()
                .object(advertResponse)
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.ADVERT_DELETED)
                .build();
    }

    //bilgichoca
    public Advert isAdvert(Long advertTypeId) {
        Advert adverta = null;
        for (Advert advert : advertRepository.findAll()) {
            if (advert.getId().equals(advertTypeId)) {
                adverta = advert;
                break;
            }
        }

        // Belirtilen advertTypeId ile eşleşen bir reklam bulunamadı
        if (adverta == null) {
            throw new IllegalStateException(ErrorMessages.ADVERT_TYPE_IN_USE_ERROR_MESSAGE);
        }

        return adverta;
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
        int totalTourRequests = advertRepository.countByAdvert(advert);
        int totalViews = advert.getViewCount();
        // Popülerlik puanı hesaplaması
        return 3 * totalTourRequests + totalViews;
    }



    // NOT: This method wrote for Report.
    public Long countAllAdvert() {
       return advertRepository.countAllAdvert();
    }

    // NOT: This method wrote for Report.
   public List<Advert> findAdvertsByFilter(LocalDate beginningDate,
                                           LocalDate endingDate,
                                           Category category,
                                           Advert_Type advertType) {

      return advertRepository.findAdvertsByFilter(beginningDate, endingDate,category, advertType);
   }

   public Advert findAdvertById(Long id){
        return advertRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.ADVERT_NOT_FOUND));
   }


}


