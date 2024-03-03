package com.project.payload.request.business;

import com.project.entity.business.Category;
import com.project.entity.business.helperentity.Advert_Type;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GetAdvertsReportRequest {

    @NotNull
    private LocalDate beginningDate;

    @NotNull
    private LocalDate endingDate;

    @NotNull
    private Category category;

    @NotNull
    private Advert_Type advertType;

    private  Boolean isActive;

}
