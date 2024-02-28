package com.project.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.entity.business.helperentity.Category_Property_Key;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
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

    //default olarak 0 degeri nasil verebilirim?
    private Boolean built_in;

    @NotNull(message = "Please enter seq")
    private Integer seq;

    @NotNull(message = "Please enter slug")
    @Size( min=5, max = 200,message = "Icon must be maximum 50 characters")
    private String slug;

    @NotNull()
    private Boolean isActive;

    @NotNull(message ="Create date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ssZ")
    private LocalDateTime create_at;

    @NotNull()
    private LocalDateTime update_at;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Category_Property_Key> category_property_keys;


    @OneToMany(mappedBy = "category_id",cascade = CascadeType.REMOVE)
    private List<Advert> adverts;



}
