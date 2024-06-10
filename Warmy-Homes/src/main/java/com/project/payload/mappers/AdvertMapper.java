package com.project.payload.mappers;

import com.project.entity.business.Advert;
import com.project.entity.business.Image;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.request.business.AdvertRequestCreate;
import com.project.payload.request.business.AdvertRequestUpdateAdmin;
import com.project.payload.request.business.AdvertRequestUpdateAuth;
import com.project.payload.request.business.helperrequest.AdvertForQueryRequest;
import com.project.payload.response.business.AdvertPageableResponse;
import com.project.payload.response.business.AdvertResponse;
import com.project.payload.response.business.ImageDTO;
import com.project.payload.response.business.ImageResponse;
import com.project.payload.response.business.helperresponse.AdvertForSlugResponse;
import com.project.payload.response.business.helperresponse.CityForAdvertResponse;
import com.project.repository.business.ImageRepository;
import com.project.service.business.ImageService;
import com.project.service.helper.CategoryHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdvertMapper {
    private final CategoryHelper categoryHelper;

    private final ImageRepository imageRepository;


    // * title i slug a cevirirken kullanacagiz
    public String slugBuilt (String title , Long id) {
        return title.toLowerCase() // Küçük harfe çevir
                .replaceAll("[^a-z0-9]+", "-") // harf veya rakam olmayan karakterleri tire ile değiştir
                .replaceAll("^-|-$", ""); // Baştaki ve sondaki tireleri kaldır
    }

    public Advert mapAdvertQueryToAdvert(AdvertForQueryRequest advertForQueryRequest){
        return Advert.builder()
                .title(advertForQueryRequest.getQ())
                .description(advertForQueryRequest.getQ())
                .advert_type(advertForQueryRequest.getAdvert_type_id())
                .category(advertForQueryRequest.getCategory_id())
                .price(advertForQueryRequest.getPrice_start())
                .price(advertForQueryRequest.getPrice_end())
                .status(advertForQueryRequest.getStatus())
                .build();
    }

    public Advert mapAdvertRequestToAdvert(AdvertRequestCreate advertRequest){


        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .price(advertRequest.getPrice())
                .location(advertRequest.getLocation())
                .build();
    }

    public Advert mapAdvertUpdateRequestToAdvert(AdvertRequestUpdateAuth advertRequest){


        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .price(advertRequest.getPrice())
                .location(advertRequest.getLocation())
                .isActive(advertRequest.getIs_active())
                .category_property_values(advertRequest.getCategory_property_values())
                .build();
    }
    public Advert mapAdvertUpdateAdminRequestToAdvert(AdvertRequestUpdateAdmin advertRequest){


        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .price(advertRequest.getPrice())
                .category_property_values(advertRequest.getCategory_property_values())
                .build();
    }
    public Advert mapSaveAdvertRequestToAdvert(AdvertRequestCreate advertRequest){


        return Advert.builder()
                .title(advertRequest.getTitle().toLowerCase())
                .slug(advertRequest.getTitle()) //create ve update isleminde kullanilmasi gerek sadece
                .description(advertRequest.getDescription())
                .price(advertRequest.getPrice())
                .location(advertRequest.getLocation())
                .build();
    }


    public AdvertResponse mapAdvertToAdvertResponse(Advert advert){

        // Advert'e ait image ID'lerini al
        List<Long> imgIDs = imageRepository.findByAdvertId(advert.getId());

        // Image ID'lerini kullanarak Image nesnelerini al

        List<Image> images = new ArrayList<>();
        for (Long id : imgIDs) {
            Image image = imageRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_NOT_FOUND_MESSAGE, id)));
            if (image != null) {
                images.add(image);
            }
        }

        // Image nesnelerini ImageResponse nesnelerine dönüştür
        List<ImageResponse> imageResponses = images.stream()
                .map(this::mapImageToImageDTO)
                .collect(Collectors.toList());

        return AdvertResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .advert_type_id(advert.getAdvert_type().getTitle())
                .is_active(advert.getIsActive())
                .country_id(advert.getCountry().getName())
                .city_id(advert.getCity().getName())
                .district(advert.getDistrict().getName())
                .images(images)
                .location(advert.getLocation())
                .build();
    }

    public ImageResponse mapImageToImageDTO(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .data(image.getData()) // Büyük veri alanı dahil edildi
                .name(image.getName())
                .type(image.getType().name()) // Enum olarak saklandığı varsayılarak
                .featured(image.getFeatured())
                .build();
    }





    public AdvertForSlugResponse mapAdvertGetSlugToAdvertResponse(Advert advert){
        return AdvertForSlugResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .advertTypeName(advert.getAdvert_type().getTitle())
                .category_property_values(advert.getCategory_property_values())
                .update_at(advert.getUpdated_at())
                .status(advert.getStatus())
                .view_count(advert.getViewCount())
                .countryName(advert.getCountry().getName())
                .cityName(advert.getCity().getName())
                .districtName(advert.getDistrict().getName())
                .images(advert.getImages())
                .location(advert.getLocation())
                .build();
    }

    public AdvertPageableResponse mapPageAdvertToAdvertResponse(Advert advert){
        return AdvertPageableResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .price(advert.getPrice())
                .advertTypeName(advert.getAdvert_type().getTitle())
                .countryName(advert.getCountry().getName())
                .cityName(advert.getCity().getName())
                .districtName(advert.getDistrict().getName())
                .images(advert.getImages())
                .location(advert.getLocation())
                .createdAt(advert.getCreatedAt())
                .is_active(advert.getIsActive())
                .view_count(advert.getViewCount())
                .status(advert.getStatus())
                .tourRequestList(advert.getTourRequestList())
                .build();
    }
    public Page<AdvertPageableResponse> mapQueryPageAdvertToAdvertResponse(Page<Advert> advert){
        return null;
    }
    public AdvertResponse mapSaveAdvertToAdvertResponse(Advert advert){
        return AdvertResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .location(advert.getLocation())
                .advert_type_id(advert.getAdvert_type().getTitle())
                .country_id(advert.getCountry().getName())
                .city_id(advert.getCity().getName())
                .district(advert.getDistrict().getName())
                .location(advert.getLocation())
                .category_id(advert.getCategory().getTitle())
                .images(advert.getImages())
                .category_property_values(advert.getCategory_property_values())
                .build();
    }


    public CityForAdvertResponse mapCityForAdvertToCityForAdvertResponse(String city, Integer amount){

        return CityForAdvertResponse.builder()
                .city(city)
                .amount(amount)
                .build();
    }


    public List<AdvertResponse> mapAdvertToAdvertResponse(List<Advert> adverts) {
        List<AdvertResponse> responses = new ArrayList<>();

        for (Advert advert : adverts) {
            // Advert'e ait image ID'lerini al
            List<Long> imgIDs = imageRepository.findByAdvertId(advert.getId());

            // Image ID'lerini kullanarak Image nesnelerini al
            List<Image> images = new ArrayList<>();
            for (Long id : imgIDs) {
                Image image = imageRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_NOT_FOUND_MESSAGE, id)));
                if (image != null) {
                    images.add(image);
                }
            }

            // Image nesnelerini response'a uygun formatta dönüştür
            List<ImageResponse> imageUrls = images.stream()
                    .map(this::mapImageToImageDTO)
                    .collect(Collectors.toList());

            AdvertResponse response = AdvertResponse.builder()
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .price(advert.getPrice())
                    .advert_type_id(advert.getAdvert_type().getTitle())
                    .country_id(advert.getCountry().getName())
                    .city_id(advert.getCity().getName())
                    .id(advert.getId())
                    .district(advert.getDistrict().getName())
                    .category_id(advert.getCategory().getTitle())
                    .images(images) // Burada imageUrls listesini ekliyoruz
                    .location(advert.getLocation())
                    .build();
            responses.add(response);
        }
        return responses;
    }


    public Page<AdvertResponse> mapAdvertToAdvertResponse(Page<Advert> adverts) {
        return adverts.map(advert -> {
            // Advert'e ait image ID'lerini al
            List<Long> imgIDs = imageRepository.findByAdvertId(advert.getId());

            // Image nesnelerini almak için bir liste oluştur
            List<Image> images = new ArrayList<>();
            for (Long id : imgIDs) {
                Image image = imageRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.IMAGE_NOT_FOUND_MESSAGE, id)));
                if (image != null) {
                    images.add(image);
                }
            }



            return AdvertResponse.builder()
                    .title(advert.getTitle())
                    .description(advert.getDescription())
                    .price(advert.getPrice())
                    .advert_type_id(advert.getAdvert_type().getTitle())
                    .country_id(advert.getCountry().getName())
                    .city_id(advert.getCity().getName())
                    .id(advert.getId())
                    .district(advert.getDistrict().getName())
                    .category_id(advert.getCategory().getTitle())
                    .images(images)
                    .location(advert.getLocation())
                    .build();
        });
    }


}
