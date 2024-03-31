package com.project.payload.response.business.helperresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.entity.business.*;
import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.business.helperentity.Category_Property_Value;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertForSlugResponse {

    private  Long id;

    private String title;


    private String description;


    //private String slug;


    private Double price;


    private byte status;



    private Integer view_count;

    private String location;

    private String  advertTypeName;

    private String countryName;

    private String cityName;

    private String districtName;


    private List<Image> images;

    private String  categoryName;

    //private LocalDateTime createdAt;

    private LocalDateTime update_at;

    private List<Category_Property_Value> category_property_values;

    //private List<Tour_Request> tourRequestList;

    // private List<Log> logList;
}
