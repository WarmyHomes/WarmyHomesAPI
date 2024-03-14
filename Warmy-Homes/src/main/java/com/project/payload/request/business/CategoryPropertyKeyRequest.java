package com.project.payload.request.business;

import com.project.entity.business.Category;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyRequest {

    @NotNull(message = "Please enter name")
    @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
    private String name;

    private Category category;

}
