package com.project.entity.business;

import com.project.entity.business.helperentity.Advert_Type;
import com.project.entity.business.helperentity.Category_Property_Value;
import com.project.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "advert_type_id", nullable = false)
    private Advert_Type advert_type_id;

    @Column(name = "desc", length = 300)
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country_id;

    @Column(name = "slug", nullable = false, length = 200)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city_id;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "built_in", nullable = false)
    private Boolean builtIn;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category_id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "advert")
    private List<Category_Property_Value> category_property_values;

    @OneToMany(mappedBy = "advert_id", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tourRequestList;

    @OneToMany(mappedBy = "advert_id", cascade = CascadeType.REMOVE)
    private List<Log> logList;

}
