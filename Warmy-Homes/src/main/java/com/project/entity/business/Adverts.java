package com.project.entity.business;


import com.project.entity.business.helperentity.Advert_Types;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_advert") // Tablo adınızı buraya yazın
public class Adverts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String location;



    private String title;

    @ManyToOne
    @JoinColumn(name = "advert_type_id")
    private Advert_Types advert_type_id;

    @Column(name = "desc", length = 300)
    private String desc;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryIid;

    private String slug;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City cityId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "built_in", nullable = false)
    private boolean builtIn;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Cate categoryId;

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
