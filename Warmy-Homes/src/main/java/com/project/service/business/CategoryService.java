package com.project.service.business;

import com.project.entity.business.Category;
import com.project.payload.request.business.CategoryDTO;
import com.project.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategories(String query, int page, int size, String sort, String type) {
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
return  null;
        // CategoryDTO listesine dönüştürme
       //return categoryPage.getContent().stream()
       //        .map(this::mapCategoryToCategoryRequest)
       //        .collect(Collectors.toList());
    }

    // Category entity'sini CategoryDTO'ya dönüştüren metod

}
