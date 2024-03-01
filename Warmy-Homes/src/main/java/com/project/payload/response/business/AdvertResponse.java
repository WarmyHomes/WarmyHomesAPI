package com.project.payload.response.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.entity.business.Log;
import com.project.entity.business.Tour_Request;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdvertResponse {


    private  Long id;

    private String title;


    private String description;


    private String slug;


    private Double price;


    private Integer status;


    private Boolean built_in;


    private Boolean is_active;


    private Integer view_count;

    private String location;

    private Advert_Type advert_type_id;

    private Long country_id;

    private Long city_id;

    private Long district;

    private User user;

    private Long category_id;

    private LocalDateTime createdAt;

    private LocalDateTime update_at;

    private List<Object> category_property_values;

    private List<Object> tourRequestList;

    private List<Object> logList;
}
