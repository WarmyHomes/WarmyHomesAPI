package com.project.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.business.helperentity.Category_Property_Key;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_categories")
@Builder(toBuilder = true)
public class Category {
    //SongulCelik

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter title")
    @Size(max = 150,message = "Title must be maximum 150 characters")
    private String title;

    @NotNull(message = "Please enter icon")
    @Size(max = 50,message = "Icon must be maximum 50 characters")
    private String icon;


    private Boolean built_in=false;

    @NotNull(message = "Please enter seq")
    private Integer seq;

    //todo notnal olmali mi ?
   // @NotNull(message = "Please enter slug")
    @Size( min=5, max = 200,message = "Icon must be maximum 50 characters")
    private String slug;

    @NotNull()
    private Boolean is_active;

    @NotNull(message ="Create date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime create_at;


    private LocalDateTime update_at;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Category_Property_Key> category_property_keys;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Advert> adverts;



}
