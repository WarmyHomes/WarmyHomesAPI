package com.project.payload.response.business;

import com.project.entity.business.Advert;
import com.project.entity.business.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllReportResponse {

    private Long published_categories;

    private Long published_advert;

    private Long advert_type;

    // private Long tour_requests;

    private Long customers;

}
