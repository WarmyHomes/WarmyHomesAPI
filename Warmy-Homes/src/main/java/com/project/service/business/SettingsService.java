package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import com.project.entity.user.UserRole;
import com.project.repository.business.*;

import com.project.repository.helperRepository.CategoryPropertyKeyRepository;
import com.project.repository.helperRepository.CategoryPropertyValueRepository;
import com.project.repository.user.UserRepository;
import com.project.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final AdvertRepository advertRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final AdvertTypesRepository advertTypesRepository;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AddressCountryRepository addressCountryRepository;
    private final AddressCityRepository addressCityRepository;
    private final AddressDistrictRepository addressDistrictRepository;
    private final TourRequestRepository tourRequestRepository;




    public void resetDatabase() {
        // Tüm tablolardaki kayıtları sıfırlamak ve "built-in" alanı true olanları
        // korumak için gerekli işlemleri burada gerçekleştirin
        // Gerekirse ilgili repository sınıflarını kullanarak veritabanı işlemlerini yapın

        // Tüm advert kayıtlarını getir
        List<Advert> allAdverts = advertRepository.findAll();

        // Her advert için kontrol et
        for (Advert advert : allAdverts) {
            if (!advert.getBuiltIn()) {
                // Eğer advert'in built-in alanı false ise sil
                advertRepository.delete(advert);
            }
        }

        imageRepository.deleteAll();

        List<Category> allCategory=categoryRepository.findAll();
        for (Category category:allCategory){
            if (!category.getBuilt_in()){
                categoryRepository.deleteAll();
            }
        }

        advertTypesRepository.deleteAll();

        List<Advert_Type> allAdvertType=advertTypesRepository.findAll();
        for (Advert_Type advertType:allAdvertType){
            if (!advertType.getBuiltIn()){
                advertTypesRepository.deleteAll();
            }
        }



    }




}
