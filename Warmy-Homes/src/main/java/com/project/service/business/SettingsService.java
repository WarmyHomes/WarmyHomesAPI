package com.project.service.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.AdvertTypesRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.business.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final AdvertRepository advertRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final AdvertTypesRepository advertTypesRepository;
    private


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
            if (!advertType.getBuiltin;){
                advertTypesRepository.deleteAll();
            }
        }



    }




}
