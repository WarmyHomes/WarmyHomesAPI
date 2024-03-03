package com.project.payload.response.business;

import com.project.entity.business.helperentity.Category_Property_Key;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryResponse {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String title;

    @NotNull
    @Size(max = 50)
    private String icon;



    @Min(0)
    private Integer seq;

    @NotNull
    @Size(min = 5, max = 200)
    private String slug;

    @NotNull
    private Boolean isActive;

    @Past
    @NotNull
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<Category_Property_Key> category_property_keys;
}
