package com.project.payload.request.business.helperrequest;

import com.project.entity.business.Category;
import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.business.helperentity.Advert_Type;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AdvertForQueryRequest {

    private String q;

    @NotNull(message = "Please enter your category_id")
    private Category category_id;

    @NotNull(message = "Please enter your advert_type_id")
    private Advert_Type advert_type_id;

    private Double price_start;

    private Double price_end;

    private byte status;

}
