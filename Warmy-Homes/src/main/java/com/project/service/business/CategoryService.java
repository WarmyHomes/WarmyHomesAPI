package com.project.service.business;

import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.CategoryMapper;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.CategoryPropertyKeyRequest;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.Category_Property_Key_Response;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.helperRepository.CategoryPropertyKeyRepository;
import com.project.repository.helperRepository.CategoryPropertyValueRepository;
import com.project.service.helper.CategoryHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryHelper categoryHelper;


    public Page<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        //todo queri null ilse neye gore siralayacak
        Page<Category> categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        return  categoryPage.map(categoryMapper::mapCategoryToResponseGetCategory);
    }

    public Page<CategoryResponse> getAllCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage;
        //queri varsa queri ye gore olan categoryleri getiriyor
        if (query != null && !query.isEmpty()) {
            categoryPage = categoryRepository.findByTitleContaining(query, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return categoryPage.map(categoryMapper::mapCategoryToResponseGetCategory);
    }


    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return categoryMapper.mapCategoryToResponse(category);
    }

    @Transactional
    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest request) {
        // Kategori başlığını formatla ve benzersiz olduğunu kontrol et.
        String formattedTitle = categoryHelper.formatAsWordsCase(request.getTitle());
        categoryHelper.isCategoryTitleUnique(formattedTitle, null);

        // CategoryRequest'ten Category entity'sine dönüşüm yap ve temel bilgileri ayarla.
        Category category = categoryMapper.mapCategoryDTOToEntity(request);
        category.setBuilt_in(false);
        category.setCreate_at(LocalDateTime.now());
        category.setTitle(formattedTitle);

        // Henüz kategori ID'si olmadığı için, önce kategori nesnesini kaydet.
        Category savedCategory = categoryRepository.save(category);

        // category_property_keys içindeki her bir propertyKey için işlem yap.
        if (request.getCategory_property_keys() != null) {
            request.getCategory_property_keys().forEach(pkRequest -> {
                // Her bir property key için createPropertyKey metodunu çağır.

                categoryPropertyKeyHelper(category,pkRequest);
               pkRequest.setCategory(category);

            });
        }

        // Slug oluştur, benzersizliğini doğrula ve kategoriye ata.
        String slug = categoryHelper.toSlug(formattedTitle, savedCategory.getId());
        categoryHelper.validateCategorySlugUniqueness(slug, savedCategory.getId());
        savedCategory.setSlug(slug);

        // Güncellenmiş kategori bilgileriyle kategoriyi tekrar kaydet.
        savedCategory = categoryRepository.save(savedCategory);

        // Oluşturulan kategoriyi dönüştür ve response oluştur.
        CategoryResponse categoryResponse = categoryMapper.mapCategoryToResponse(savedCategory);
        return ResponseMessage.<CategoryResponse>builder()
                .object(categoryResponse)
                .message(SuccessMessages.CATEGORY_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }




    public ResponseMessage<CategoryResponse> updateCategory(Long id, CategoryRequest request) {

        //category var mi varsa dondur yoksa hata firlat
        Category category = categoryHelper.findCategoryById(id);
        //category uniq mi?
        String title=   categoryHelper.formatAsWordsCase(request.getTitle());
        categoryHelper.isCategoryTitleUnique( title, id);
        // categoryHelper.isCategoryTitleUnique(request.getTitle(), id);
        //sluq uniq mi ?
        categoryHelper.validateCategorySlugUniqueness(categoryHelper.toSlug(category.getTitle(),id),id);

        //todo category.getBuilt_in() kontrol et ?
        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be updated." + category.getBuilt_in());
        }
        List<Category_Property_Key_Response> prpertlist= findPropertyKeysByCategoryId(id);

        // Güncelleme işlemleri
        category.setTitle(request.getTitle());
        category.setIcon(request.getIcon());
        category.setSeq(request.getSeq());

        //todo sluq i unutma
        category.setSlug(categoryHelper.toSlug(category.getTitle(),id));
        // category.setSlug(request);

        category.setIs_active(request.getIs_active());
        category.setUpdate_at(LocalDateTime.now()); // Güncelleme tarihini şu anki zaman olarak ayarla

        Category savedCategory = categoryRepository.save(category);


        return ResponseMessage.<CategoryResponse>builder()
                .object(categoryMapper.mapCategoryToResponse(savedCategory))
                .message(SuccessMessages.CATEGORY_UPDATE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public CategoryResponse deleteCategory(Long id) {

        //category var mi varsa dondur yoksa hata firlat
        Category category = categoryHelper.findCategoryById(id);
        ;

        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be deleted.");
        }

        //bagli ilan var mi ?
        //if (!advertRepository.findByCategoryId(category.getId()).isEmpty()) {
        //    throw new UnsupportedOperationException("Category with related advertisements cannot be deleted.");
        //}

        categoryRepository.delete(category);
        return categoryMapper.mapCategoryToResponse(category);
    }

    public List<Category_Property_Key_Response> findPropertyKeysByCategoryId(Long categoryId) {
        // Kategori varlığını kontrol et
        //category var mi varsa dondur yoksa hata firlat
        Category category = categoryHelper.findCategoryById(categoryId);

        // Kategoriye ait özellik anahtarlarını bul
        List<Category_Property_Key> propertyKeys = propertyKeyRepository.findByCategoryId(categoryId);

        // PropertyKey nesnelerini PropertyKeyResponse DTO'larına dönüştür
        return propertyKeys.stream()
                .map(categoryMapper::Category_Property_KeyToResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<Category_Property_Key_Response> createPropertyKey(Long categoryId, CategoryPropertyKeyRequest propertyKeyRequest) {

        // Kategori var mı kontrol et, yoksa hata fırlat
        Category category = categoryHelper.findCategoryById(categoryId);

        // Yeni property key oluştur
        Category_Property_Key propertyKey = Category_Property_Key.builder()
                .name(propertyKeyRequest.getName())
                .category(category)
                .built_in(false)
                .build();

      Category_Property_Key  savedPropertyKey = categoryPropertyKeyHelper(category,propertyKey);


        // Response oluştur ve dön
        return ResponseMessage.<Category_Property_Key_Response>builder()
                .object(categoryMapper.Category_Property_KeyToResponse(savedPropertyKey))
                .message(SuccessMessages.PREPORTY_KEY_CREATED)
                .httpStatus(HttpStatus.OK)
                .build();
    }

private Category_Property_Key categoryPropertyKeyHelper(Category category,Category_Property_Key propertyKey){

    // Oluşturulan propertyKey'i veritabanına kaydet
    Category_Property_Key savedPropertyKey = propertyKeyRepository.save(propertyKey);

    // Eğer kategori'nin property key listesi null ise, yeni bir liste oluştur
    if (category.getCategory_property_keys() == null) {
        category.setCategory_property_keys(new ArrayList<>());
    }

    // Yeni property key'i kategori'nin property key listesine ekle
    category.getCategory_property_keys().add(savedPropertyKey);

    // Kategori nesnesini güncelle (kategoriye yeni eklenen property key ile birlikte)
     categoryRepository.save(category); // categoryRepository, Category nesnelerini yönetmek için kullanılan repository olmalı

    return savedPropertyKey;

    }





    public ResponseMessage<Category_Property_Key_Response> updatePropertyKey(Long id, CategoryPropertyKeyRequest request) {
        Category_Property_Key propertyKey = propertyKeyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property key not found with id: " + id));

        if (propertyKey.getBuilt_in()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Built-in property key cannot be updated.");
        }

        propertyKey.setName(request.getName());
        propertyKey.setBuilt_in(false);
        // propertyKey.setCategoryId(request.getCategoryId());
        if (request.getCategory() != null) propertyKey.setCategory(request.getCategory());
        // diger setlenmesi gereken yerler varsa setlenmeli

        Category_Property_Key updatedPropertyKey = propertyKeyRepository.save(propertyKey);


        return ResponseMessage.<Category_Property_Key_Response>builder()
                .object(categoryMapper.Category_Property_KeyToResponse(updatedPropertyKey))
                //TODO MESAJI DUZELT
                .message(SuccessMessages.PROPERTY_KEY_UPDATE)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    public ResponseMessage<Category_Property_Key_Response> deletePropertyKey(Long id) {
        Category_Property_Key propertyKey = propertyKeyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found with id: " + id));

        if (propertyKey.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be deleted.");
        }
        // İlk olarak ilişkili CategoryPropertyValue kayıtlarını sil
        //categoryPropertyValueRepository.deleteByCategoryPropertyKeyId(propertyKey.getId());

        // Sonra CategoryPropertyKey nesnesini sil
        propertyKeyRepository.delete(propertyKey);

        return ResponseMessage.<Category_Property_Key_Response>builder()
                .object(categoryMapper.Category_Property_KeyToResponse(propertyKey))
                .message(SuccessMessages.PROPERTY_KEY_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    public CategoryResponse getCategoryBySlug(String slug) {

        //categoryHelper.validateCategorySlugUniqueness(slug, null);
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with slug " + slug + " not found"));


        return categoryMapper.mapCategoryToResponse(category);
    }









    // NOT: This method wrote for Report.
    public Long countAllCategories(){
        return categoryRepository.countAllCategory();
    }









}



