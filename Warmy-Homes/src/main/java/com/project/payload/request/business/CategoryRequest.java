package com.project.payload.request.business;

import com.project.entity.business.helperentity.Category_Property_Key;
import com.project.service.helper.CategoryHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {



    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title can have at most 150 characters")
    private String title;



    @NotBlank(message = "Icon is required")
    @Size(max = 50, message = "Icon can have at most 50 characters")
    private String icon;



    @NotNull(message = "Sequence is required")
    private Integer seq;

   //@NotBlank(message = "Slug is required")
   //@Size(min = 5, max = 200, message = "Slug must be between 5 and 200 characters")
   //private String slug;

    @NotNull(message = "Active status is required")
    private Boolean is_active=true;


    private List<Category_Property_Key> category_property_keys;

}
