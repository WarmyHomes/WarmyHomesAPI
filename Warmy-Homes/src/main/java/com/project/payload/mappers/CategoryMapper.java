package com.project.payload.mappers;

import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.payload.request.business.CategoryPropertyKeyRequest;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.Category_Property_Key_Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryMapper {

    public  Category mapCategoryDTOToEntity(@NotNull CategoryRequest categoryDTO) {
        return Category.builder()
               // .id(categoryDTO.getId())
                .title(categoryDTO.getTitle())
                .icon(categoryDTO.getIcon())
                .seq(categoryDTO.getSeq())
               // .slug(categoryDTO.getSlug())
                .is_active(categoryDTO.getIs_active())
                .build();

                // !!!!! dikkat dto dan creat  ve update tarihi gelmeyecek db den alinacak
               // !!!!!.create_at(LocalDateTime.parse(categoryDTO.get))
               // !!!!! .updateAt(categoryDTO.getUpdateAt() != null ? LocalDateTime.parse(categoryDTO.getUpdateAt()) : null)

    }

    public  CategoryResponse mapCategoryToResponse( Category category) {
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


    //tum Category nesnelerinin istendigi yerde property value lerin geri donusu engellendi performasn artisi saglandi
    public CategoryResponse mapCategoryToResponseGetCategory(Category category) {
        List<Category_Property_Key> propertyKeyResponses = category.getCategory_property_keys().stream()
                .map(key -> new Category_Property_Key(key.getId(), key.getName(), key.getBuilt_in()))
                .collect(Collectors.toList());

        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .category_property_keys(propertyKeyResponses) // Güncellenmiş kısım
                .icon(category.getIcon())
                .seq(category.getSeq())
                .slug(category.getSlug())
                .isActive(category.getIs_active())
                .createAt(category.getCreate_at())
                .updateAt(category.getUpdate_at() != null ? category.getUpdate_at() : null)
                .build();
    }


    public Category_Property_Key mapPropertyKeyRequestToPropertyKey(Category_Property_Key request) {
        Category_Property_Key propertyKey = Category_Property_Key.builder()
                .name(request.getName())
                .category(request.getCategory()) // Bağlı olduğu kategoriyi ayarla
                .build();
        return propertyKey;
    }


    public  Category_Property_Key Category_Property_KeyRequestToEntity(CategoryPropertyKeyRequest request) {
        return  Category_Property_Key.builder()
                .name(request.getName())

                // Category ID set etme işlemi burada gerçekleştirilebilir.
                .build();
    }


    public  Category_Property_Key_Response Category_Property_KeyToResponse(Category_Property_Key propertyKey) {
        return Category_Property_Key_Response.builder()
                .id(propertyKey.getId())
                .name(propertyKey.getName())
                // İlave dönüşümler gerekiyorsa burada ekleyebilirsiniz.
                .build();
     }



}
