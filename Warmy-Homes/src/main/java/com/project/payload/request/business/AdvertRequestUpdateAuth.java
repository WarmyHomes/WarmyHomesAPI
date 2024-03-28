package com.project.payload.request.business;

import com.project.entity.business.*;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.business.helperentity.Category_Property_Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdvertRequestUpdateAuth {


    @NotNull(message = "Please enter your title")
    @Size(min = 5, max = 150,message = "Your title should be at least 5 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your title must consist of the characters .")
    private String title;

    @NotNull(message = "Please enter your username")
    @Size( max = 300,message = "Your description should be at big 300 chars")
    private String description;

    @NotNull(message = "Please enter your price")
    private Double price;

    //@NotNull
    //private Integer status=0;

    //private Boolean built_in=false;

    @NotNull
    private Boolean is_active=true;

//    @NotNull
//    private Integer view_count=0;

    @NotNull(message = "Please enter your location")
    private String location;

    @NotNull(message = "Please enter your advert type")
    private Long advert_type_id;

    @NotNull(message = "Please enter your country")
    private Long country_id;

    @NotNull(message = "Please enter your city")
    private Long city_id;

    @NotNull(message = "Please enter your district")
    private Long district;

//    @NotNull(message = "Please enter your user")
//    private User user;

    @NotNull(message = "Please enter your category")
    private Long category_id;

//    @NotNull(message = "Please enter your advert type")
    private List<Long> images;


//    @NotNull
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
//    private LocalDateTime createdAt;
//
//    @NotNull
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
//    private LocalDateTime update_at;

    @NotNull(message = "Please enter your category_property_values")
    private List<Category_Property_Value> category_property_values;

//    @NotNull(message = "Please enter your tourRequestList")
//    private List<Tour_Request> tourRequestList;

//    @NotNull(message = "Please enter your logList")
//    private List<Long> logList;




}
