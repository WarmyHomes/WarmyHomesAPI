package com.project.service.business;

import com.project.entity.business.Category;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.CategoryMapper;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.repository.business.CategoryRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PageableHelper pageableHelper;



    public List<CategoryResponse> getCategories(String query, int page, int size, String sort, String type) {
        // Varsayılan değerlerin atanması
        if (page < 0) page = 0;
        if (size <= 0) size = 20;
        if (sort == null || sort.isEmpty()) sort = "id"; // Varsayılan olarak id'ye göre sırala
        if (type == null || type.isEmpty()) type = "asc"; // Varsayılan olarak artan sıralama

        // Sayfa bilgilerine göre sayfalama nesnesinin oluşturulması
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));

                // Arama kriterine göre kategorilerin getirilmesi
        Page<Category> categoryPage;
        if (query != null && !query.isEmpty()) {
            categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        } else {
            categoryPage = categoryRepository.findByIsActiveTrue(pageable);
        }


       return categoryPage.getContent().stream()
              .map(CategoryMapper::mapCategoryToResponse)
               .collect(Collectors.toList());

    }


    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(String query, int page, int size, String sort, String type) {
        // Default degerleri atadik
        if (page < 0) page = 0;
        if (size <= 0) size = 20;
        if (sort == null || sort.isEmpty()) sort = "id";
        if (type == null || type.isEmpty()) type = "asc";

              //  pageable objesi olusturduk
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));

        // sorguyu yaptik
        Page<Category> categoryPage;
        if (query != null && !query.isEmpty()) {
            categoryPage = categoryRepository.findByTitleContainingAndIsActiveTrue(query, pageable);
        } else {
            categoryPage = categoryRepository.findByIsActiveTrue(pageable);
        }

        // Map Category entitiyi response dondurduk
        return categoryPage.getContent().stream()
                .map(CategoryMapper::mapCategoryToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return CategoryMapper.mapCategoryToResponse(category);
    }


    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = CategoryMapper.mapCategoryDTOToEntity(request);
        category.setCreate_at(LocalDateTime.now()); // creat tarihini aldik

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapCategoryToResponse(savedCategory);
    }
}
