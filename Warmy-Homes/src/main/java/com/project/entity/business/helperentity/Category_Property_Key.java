package com.project.entity.business.helperentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.entity.business.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_category_property_keys")
@Builder(toBuilder = true)
public class Category_Property_Key {
    //SongulCelik

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter name")
    @Size(min = 2, max = 80,message = "Name must be maximum 80 characters")
    private String name;

    //default olarak 0 degeri nasil verebilirim?
    //defoult olarak 0 degilde false verilmesi gerekmiyor mu? (E.aktas)
    //"private Boolean built_in1=false"; seklinde verilebilir. Booleanin defoult degeri de null mis bu arada(E.aktas)
    private Boolean built_in =false;


    @ManyToOne
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "category_property_key", cascade = CascadeType.REMOVE)
    private List<Category_Property_Value> category_property_values;


    public Category_Property_Key(Long id, String name, Boolean built_in) {
        this.id = id;
        this.name = name;
        this.built_in = built_in;
    }
}
