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
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;
    private final CategoryHelper categoryHelper;


    public List<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        //todo queri null ilse neye gore siralayacak
        Page<Category> categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        return categoryPage.getContent().stream()
                .map(categoryMapper::mapCategoryToResponse)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> getAllCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage;
        //queri varsa queri ye gore olan categoryleri getiriyor
        if (query != null && !query.isEmpty()) {
            categoryPage = categoryRepository.findByTitleContaining(query, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return categoryPage.getContent().stream()
                .map(categoryMapper::mapCategoryToResponse)
                .collect(Collectors.toList());
    }


    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return categoryMapper.mapCategoryToResponse(category);
    }

    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest request) {

        String title=   categoryHelper.formatAsWordsCase(request.getTitle());
        categoryHelper.isCategoryTitleUnique( title, null);



        // TODO category creat ederken dikkat edilmesi gerekenleri incele eksik kalmasin
        Category category = categoryMapper.mapCategoryDTOToEntity(request);
        category.setBuilt_in(false);
        category.setCreate_at(LocalDateTime.now());
        category.setTitle(title);

        // Yeni bir Category_Property_Key listesi oluştur
        List<Category_Property_Key> propertyKeys = new ArrayList<>();

// İstekten gelen property key'ler üzerinde döngü yap
        // for (CategoryPropertyKeyRequest propertyKeyRequest : request.getCategory_property_keys()) {
        //     // createPropertyKey metodu ile yeni bir property key oluştur
        //     Category_Property_Key createdPropertyKey = createPropertyKey(category.getId(), propertyKeyRequest);

        //     // Oluşturulan property key'i listeye ekle
        //     if (createdPropertyKey != null) { // createPropertyKey başarılı bir şekilde bir property key oluşturduysa
        //         propertyKeys.add(createdPropertyKey);
        //     }
        // }

// Oluşturulan property key listesini category nesnesine ata
        category.setCategory_property_keys(propertyKeys);


        ;

        Category savedCategory;
        savedCategory = categoryRepository.save(category);


        String sluq= categoryHelper.toSlug(savedCategory.getTitle(),savedCategory.getId());
        categoryHelper.validateCategorySlugUniqueness(sluq, savedCategory.getId());
        savedCategory.setSlug(sluq);

        //sluq olustururken id nasil eklenecek ?? 2 defa mi gitmeli
        savedCategory = categoryRepository.save(category);

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
        Category category = categoryHelper.findCategoryById(categoryId);;

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
                //.categoryId(category.getId()) // Bu propertyKey'e kategori bilgisini ekle
                .built_in(false)
                .build();

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

        // Response oluştur ve dön
        return ResponseMessage.<Category_Property_Key_Response>builder()
                .object(categoryMapper.Category_Property_KeyToResponse(savedPropertyKey))
                .message(SuccessMessages.PROPERTY_KEY_CREATED) // SuccessMessages.PREPORTY_KEY_CREATED olmalıydı, küçük bir yazım hatası var gibi
                .httpStatus(HttpStatus.OK)
                .build();
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



