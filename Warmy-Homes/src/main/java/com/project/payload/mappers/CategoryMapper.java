package com.project.payload.mappers;

import com.project.entity.business.Category;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;

public class CategoryMapper {

    public static Category mapCategoryDTOToEntity(CategoryRequest categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .title(categoryDTO.getTitle())
                .icon(categoryDTO.getIcon())
                .seq(categoryDTO.getSeq())
                .slug(categoryDTO.getSlug())
                .is_active(categoryDTO.getIs_active())
                .build();

                // !!!!! dikkat dto dan creat  ve update tarihi gelmeyecek db den alinacak
               // !!!!!.create_at(LocalDateTime.parse(categoryDTO.get))
               // !!!!! .updateAt(categoryDTO.getUpdateAt() != null ? LocalDateTime.parse(categoryDTO.getUpdateAt()) : null)

    }

    public static CategoryResponse mapCategoryToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .category_property_keys(category.getCategory_property_keys())
                .icon(category.getIcon())
                .seq(category.getSeq())
                .slug(category.getSlug())
                .isActive(category.getIs_active())
                .createAt(category.getCreate_at())
                .updateAt(category.getUpdate_at() != null ? category.getUpdate_at() : null)
                .build();
    }
}
