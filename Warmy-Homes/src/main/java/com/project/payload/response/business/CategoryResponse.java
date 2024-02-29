package com.project.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
