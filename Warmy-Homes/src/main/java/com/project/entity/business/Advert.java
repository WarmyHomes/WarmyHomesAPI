package com.project.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.business.helperentity.AdvertStatusRole;
import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.business.helperentity.Category_Property_Value;
import com.project.entity.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "t_advert")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Advert_Type advert_type;


    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Size(min = 5, max = 200,message = "Your slug should be at least 5 chars")
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;


    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private District district;



    private byte status = 0 ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;



    private Boolean builtIn = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


    private Boolean isActive = false;


    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalDateTime createdAt;


    private Integer viewCount;

    @OneToMany( mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Image> images;

    @Column(name = "update_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<Category_Property_Value> category_property_values;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tourRequestList;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Log> logList;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

}
