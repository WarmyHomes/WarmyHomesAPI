package com.project.service.business;

import com.project.entity.business.Category;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.CategoryMapper;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AdvertRepository;
import com.project.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertRepository advertRepository;



    public List<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        return categoryPage.getContent().stream().map(CategoryMapper::mapCategoryToResponse).collect(Collectors.toList());
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
                .map(CategoryMapper::mapCategoryToResponse)
                .collect(Collectors.toList());
    }


    public CategoryResponse getCategoryById(Long id,HttpServletRequest httpServletRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id" , id));
        return CategoryMapper.mapCategoryToResponse(category);
    }

    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest request) {
       //todo ayni categoriden 1 tane mi olur?
        Category category = CategoryMapper.mapCategoryDTOToEntity(request);
        category.setCreate_at(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        CategoryResponse categoryResponse= CategoryMapper.mapCategoryToResponse(savedCategory);


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
        return CategoryMapper.mapCategoryToResponse(savedCategory);
    }

    public CategoryResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Built-in category cannot be deleted.");
        }

        //bagli ilan var mi ?
        if (!advertRepository.findByCategoryId(category.getId()).isEmpty()) {
            throw new UnsupportedOperationException("Category with related advertisements cannot be deleted.");
        }

        categoryRepository.delete(category);
        return CategoryMapper.mapCategoryToResponse(category);



    }

    public List<PropertyKeyResponse> getCategoryPropertyKeys(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return category.getPropertyKeys().stream()
                .map(propertyKey -> new PropertyKeyResponse(propertyKey.getId(), propertyKey.getName()))
                .collect(Collectors.toList());
    }

}
