package com.project.payload.mappers;

import com.project.entity.business.Category;
import com.project.payload.request.business.CategoryDTO;

import java.time.LocalDateTime;

public class CategoryMapper {

    public static Category mapCategoryDTOToEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .title(categoryDTO.getTitle())
                .icon(categoryDTO.getIcon())
                .seq(categoryDTO.getSeq())
                .slug(categoryDTO.getSlug())
               //todo buraya bak
                // .isActive(categoryDTO.isActive())
               // .createAt(LocalDateTime.parse(categoryDTO.getCreateAt()))
               // .updateAt(categoryDTO.getUpdateAt() != null ? LocalDateTime.parse(categoryDTO.getUpdateAt()) : null)
                .build();
    }

    public static CategoryResponse mapCategoryToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .icon(category.getIcon())
                .builtIn(category.isBuiltIn())
                .seq(category.getSeq())
                .slug(category.getSlug())
                .isActive(category.isActive())
                .createAt(category.getCreateAt().toString())
                .updateAt(category.getUpdateAt() != null ? category.getUpdateAt().toString() : null)
                .build();
    }
}
