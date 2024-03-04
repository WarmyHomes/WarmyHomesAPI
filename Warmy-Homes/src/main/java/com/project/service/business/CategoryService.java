package com.project.service.business;
import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Category_Property_Key;
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
import lombok.NoArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor

public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertRepository advertRepository;
    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;



    public List<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
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


    public CategoryResponse getCategoryById(Long id,HttpServletRequest httpServletRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id" , id));
        return categoryMapper.mapCategoryToResponse(category);
    }

    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest request) {
       //todo ayni categoriden 1 tane mi olur?
        Category category = categoryMapper.mapCategoryDTOToEntity(request);
        category.setCreate_at(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        CategoryResponse categoryResponse= categoryMapper.mapCategoryToResponse(savedCategory);


        return  ResponseMessage.<CategoryResponse>builder()
                .object(categoryResponse)
                .message(SuccessMessages.ADVERT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();




    }



    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be updated.");
        }

        // Güncelleme işlemleri
        category.setTitle(request.getTitle());
        category.setIcon(request.getIcon());
        category.setSeq(request.getSeq());
        category.setSlug(request.getSlug());
        category.setIs_active(request.getIs_active());
        category.setUpdate_at(LocalDateTime.now()); // Güncelleme tarihini şu anki zaman olarak ayarla

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapCategoryToResponse(savedCategory);
    }

    public CategoryResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be deleted.");
        }

      ////bagli ilan var mi ?
      //if (!advertRepository.findByCategoryId(category.getId()).isEmpty()) {
      //    throw new UnsupportedOperationException("Category with related advertisements cannot be deleted.");
      //}

        categoryRepository.delete(category);
        return categoryMapper.mapCategoryToResponse(category);
    }


    public List<Category_Property_Key_Response> findPropertyKeysByCategoryId(Long categoryId) {
        // Kategori varlığını kontrol et
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Kategoriye ait özellik anahtarlarını bul
        List<Category_Property_Key> propertyKeys = propertyKeyRepository.findByCategoryId(categoryId);

        // PropertyKey nesnelerini PropertyKeyResponse DTO'larına dönüştür
        return propertyKeys.stream()
                .map(categoryMapper::Category_Property_KeyToResponse)
                .collect(Collectors.toList());
    }




        public Category_Property_Key_Response createPropertyKey(Long categoryId, CategoryPropertyKeyRequest propertyKeyRequest) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + categoryId));

            Category_Property_Key propertyKey = Category_Property_Key.builder()
                    .name(propertyKeyRequest.getName())
                    // Diğer özelliklerini de buraya ekleyebiliriz
                    .category(category)
                    .build();

            Category_Property_Key savedPropertyKey = propertyKeyRepository.save(propertyKey);

            return categoryMapper.Category_Property_KeyToResponse(savedPropertyKey);

        }

    public Category_Property_Key_Response updatePropertyKey(Long id, CategoryPropertyKeyRequest request) {
        Category_Property_Key propertyKey = propertyKeyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property key not found with id: " + id));

        if (propertyKey.getBuilt_in()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Built-in property key cannot be updated.");
        }

        propertyKey.setName(request.getName());
        if (request.getCategory()!=null)  propertyKey.setCategory(request.getCategory());
        // diger setlenmesi gereken yerler varsa setlenmeli

        Category_Property_Key updatedPropertyKey = propertyKeyRepository.save(propertyKey);


        return  categoryMapper.Category_Property_KeyToResponse(updatedPropertyKey);
    }


    public ResponseMessage<Category_Property_Key_Response> deletePropertyKey(Long id) {
      Category_Property_Key propertyKey=  propertyKeyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found with id: " + id));

        if (propertyKey.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be deleted.");
        }
            // İlk olarak ilişkili CategoryPropertyValue kayıtlarını sil
            categoryPropertyValueRepository.deleteByCategoryPropertyKey(propertyKey);

            // Sonra CategoryPropertyKey nesnesini sil
            propertyKeyRepository.delete(propertyKey);

        return ResponseMessage.<Category_Property_Key_Response>builder()
                .object(categoryMapper.Category_Property_KeyToResponse(propertyKey))
                .message(SuccessMessages.PROPERTY_KEY_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

        }









}



