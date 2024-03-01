package com.project.service.business;

import com.project.entity.business.Category;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.CategoryMapper;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;



    public List<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        return categoryPage.getContent().stream().map(CategoryMapper::mapCategoryToResponse).collect(Collectors.toList());
    }

    public List<CategoryResponse> getAllCategories(String query, int page, int size, String sort, String type,
                                                   HttpServletRequest httpServletRequest) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        Page<Category> categoryPage = categoryRepository.findByTitleContaining(query, pageable);
        return categoryPage.getContent().stream().map(CategoryMapper::mapCategoryToResponse).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id,HttpServletRequest httpServletRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return CategoryMapper.mapCategoryToResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request,HttpServletRequest httpServletRequest) {



        Category category = CategoryMapper.mapCategoryDTOToEntity(request);
        category.setCreate_at(LocalDateTime.now());
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapCategoryToResponse(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request,HttpServletRequest httpServletRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        if (category.getBuilt_in()) {
            throw new UnsupportedOperationException("Cannot update built-in category");
        }
        category.setTitle(request.getTitle());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.mapCategoryToResponse(updatedCategory);
    }
}
