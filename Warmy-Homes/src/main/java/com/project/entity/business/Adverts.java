package com.project.entity.business;


import com.project.entity.business.helperentity.Advert_Types;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_advert") // Tablo adınızı buraya yazın
public class YourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @ManyToOne
    @JoinColumn(name = "advert_type_id", nullable = false)
    private Advert_Types advert_type_id;

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
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "built_in", nullable = false)
    private boolean builtIn;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category_id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;


}
