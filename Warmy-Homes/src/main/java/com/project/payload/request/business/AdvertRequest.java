package com.project.payload.request.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequest {

    private String location;
    private String q;
    private Long category_id;
    private Long advert_type_id;
    private Double price_start;
    private Double price_end;
    private Integer status;

}
